package br.senai.sp.cfp138.guideviagem.util;

import java.nio.charset.StandardCharsets;

import com.google.common.hash.Hashing;

public class HashUtil {

		public static String hash(String palavra) {
			//tomperro do hash
			String salt =  "b@n@n@";
			//temperar a palavra
			palavra = salt + palavra;
			//gera hash
			String hash = Hashing.sha256().hashString(palavra, StandardCharsets.UTF_8).toString();
			return hash;
		}
	
}
