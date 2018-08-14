package com.naresh.nxmlabs.domain;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.naresh.nxmlabs.domain.encryption.KeyProcessor;
import java.io.FileNotFoundException;
import java.io.FileReader;
import javax.xml.bind.DatatypeConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)

public class NxmController {
  private KeyProcessor keyProcessor;

  @Autowired
  public NxmController(KeyProcessor keyProcessor) {
    this.keyProcessor = keyProcessor;
  }

  @GetMapping("/getPublicKey")
  public ResponseEntity<String> getPublicKey() {
    return new ResponseEntity<>(DatatypeConverter.printBase64Binary(keyProcessor.getPublicKey().getEncoded()), HttpStatus.OK);
  }

  @GetMapping("/getEncryptedText")
  public ResponseEntity<Object> getEncryptedText() {
    JsonObject jsonObject = new JsonObject();

    try {
      JsonParser parser = new JsonParser();
      JsonElement jsonElement = parser.parse(new FileReader(getClass().getClassLoader().getResource("SampleTextFile.txt").getFile()));
      jsonObject = jsonElement.getAsJsonObject();
    } catch (FileNotFoundException e) {
      //log
    }
    return new ResponseEntity<>(jsonObject, HttpStatus.OK);
  }
}
