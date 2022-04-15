package br.senai.sp.cfp138.guideviagem.util;

import java.io.IOException;
import java.util.UUID;



import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;



import com.google.auth.Credentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;


@Service
public class FirebaseUtil {
	
	// variável para guardar as credenciais de acesso
	private Credentials credenciais;
	private Storage storage;
	// constante para o nome do bucket
	private final String BUCKET_NAME = "guideviagem.appspot.com";
	private final String PREFIX = "https://firebasestorage.googleapis.com/v0/b/"+BUCKET_NAME+"/o/";
	private final String SUFFIX = "?alt=media";
	private final String DOWNLOAD_URL = PREFIX + "%s" + SUFFIX;
	
	public FirebaseUtil() {
		
		// acessar o arquivo json com a chave privada
		Resource resource = new ClassPathResource("guideviagemKey.json");
		
		// gera uma credencial no firebase atraves da chave do arq
		try {
			
			credenciais = GoogleCredentials.fromStream(resource.getInputStream());
			// cria o storage para manipular os dados no firebase
			storage = StorageOptions.newBuilder().setCredentials(credenciais).build().getService();
			
		} catch (IOException e) {
			
			throw new RuntimeException(e.getMessage());
		
		}
		
	}
	
	// método para extrair a extenção do arq
	private String getExtensao(String nomeArquivo) {
		
		// extrai o trcho do arq onde está a extensão
		return nomeArquivo.substring(nomeArquivo.lastIndexOf('.'));
	
	}
	
	// método q faz o upload
	public String upload(MultipartFile arquivo) throws IOException {
		
		// gera um nome aleatorio para o arquivo
		String nomeArquivo = UUID.randomUUID().toString() + getExtensao(arquivo.getOriginalFilename());
		//criar blob id atravez do nome gerado para o arquivo
		BlobId blobId = BlobId.of(BUCKET_NAME, nomeArquivo);
		//criar blob info atravez do blobid
		BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType("media").build();
		//gravar blobInfo no storage passando os bytes do arquivo
		storage.create(blobInfo, arquivo.getBytes());
		// retorna url do arquivo gerado no storage
		return String.format(DOWNLOAD_URL, nomeArquivo);
	
	}
	
	//metodo que deleta arquivo do storage
	public void deletar(String nomeArquivo) {
		//retirar preffix e suffix da string
		nomeArquivo = nomeArquivo.replace(PREFIX, "").replace(SUFFIX, "");
		// obter blob por nome
		Blob blob = storage.get(BlobId.of(BUCKET_NAME, nomeArquivo));
		//deleta por blob
		storage.delete(blob.getBlobId());
		
	}
}
