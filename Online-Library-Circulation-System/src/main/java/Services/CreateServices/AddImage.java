package Services.CreateServices;

import java.io.ByteArrayOutputStream;
import java.util.zip.Deflater;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import Entities.Imageentity;
import Entities.Userentity;
import ExeptionHandler.NotFoundExceptionHandler.NotFoundException;
import Reponses.MessageResponse;
import Repositories.ImageentityRepository;
import Repositories.UserEntityRepository;

@Service
public class AddImage {

	@Autowired
	private ImageentityRepository imageentityRepository;

	@Autowired
	private UserEntityRepository userEntityRepository;

	public MessageResponse uploadImage(MultipartFile file, Long id) throws Exception {

		Userentity userentity = userEntityRepository.findByid(id);
		if (userentity == null) {
			throw new NotFoundException("User not found with id " + id);
		}
		
		byte[] imageData = file.getBytes();
		if (imageData == null || imageData.length == 0) {
			throw new IllegalArgumentException("Image data cannot be null or empty");
		}

		byte[] compressedImageData;
		try (ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
			Deflater compresser = new Deflater();
			compresser.setInput(imageData);
			compresser.finish();
			byte[] buffer = new byte[1024];
			while (!compresser.finished()) {
				int count = compresser.deflate(buffer);
				bos.write(buffer, 0, count);
			}
			compressedImageData = bos.toByteArray();
		} catch (Exception ex) {
			throw new Exception("Failed to compress image data", ex);
		}

		Imageentity imageDataEntity = new Imageentity();
		imageDataEntity.setName(file.getOriginalFilename());
		imageDataEntity.setType(file.getContentType());
		imageDataEntity.setImageData(compressedImageData);
		imageDataEntity.setUser(userentity);

		imageentityRepository.save(imageDataEntity);

		return new MessageResponse("File uploaded successfully: " + file.getOriginalFilename());
	}

}
