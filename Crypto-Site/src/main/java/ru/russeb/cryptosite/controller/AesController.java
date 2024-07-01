package ru.russeb.cryptosite.controller;

import lombok.Data;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.russeb.cryptosite.cipher.AesCipher;
import ru.russeb.cryptosite.model.Aes;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;


//Контоллер запросов для шифрования AES
@Controller
@RequestMapping(value = "/crypto/AES")
public class AesController {
    @GetMapping("")
    public String getAESPage(Model model) {
        List<KeySize> keySizes = Arrays.asList(
                new KeySize(128, "128 бит"), // установлено true для размера 128 бит
                new KeySize(192, "192 бита"),
                new KeySize(256, "256 бит")
        );
        model.addAttribute("aesObject", new Aes());
        model.addAttribute("keySizes", keySizes);
        return "aes";
    }

    @Data
    public static class KeySize {
        private int size;
        private String label;

        KeySize(int size, String label) {
            this.size = size;
            this.label = label;
        }
    }

    @PostMapping("/calculate")
    public String calculateAES(Model model, @ModelAttribute("aesObject") Aes aesObject, BindingResult bindingResult) throws Exception {
        if(hasMistakes(aesObject, bindingResult,model)){
            return "aes";
        }
        SecretKey secretKey = new SecretKeySpec(aesObject.getKey().getBytes(StandardCharsets.UTF_8), "AES");
        if(aesObject.isEncrypt()){
            model.addAttribute("result", AesCipher.encrypt(aesObject.getTextToCipher(),secretKey,aesObject.getIv()));
        }else{
            try {
                model.addAttribute("result",AesCipher.decrypt(aesObject.getTextToCipher(),secretKey,aesObject.getIv()));
            }catch (Exception e){
                model.addAttribute("decryptError", true);
                return "aes";
            }
        }

        model.addAttribute("formSubmitted", true);
        return "aes";

    }


    private boolean hasMistakes(Aes aesObject, BindingResult bindingResult,Model model){
        int keySize = aesObject.getKeySize();
        boolean hasMistakes = false;
        if(aesObject.getTextToCipher().isBlank()){
            hasMistakes = true;
            bindingResult.addError(new FieldError("aesObject", "textToCipher", "Текст не может быть пустым"));
            model.addAttribute("textEmpty", true);
        }
        if (keySize == 128 && aesObject.getKey().length() != 16) {
            bindingResult.addError(new FieldError("aesObject" ,"key", "Длина ключа должна быть 16 символов для 128-битного ключа"));
            hasMistakes = true;
            model.addAttribute("keySizeError", true);
        } else if (keySize == 192 && aesObject.getKey().length() != 24) {
            bindingResult.addError(new FieldError("aesObject" ,"key", "Длина ключа должна быть 24 символа для 192-битного ключа"));
            hasMistakes = true;
            model.addAttribute("keySizeError", true);
        } else if (keySize == 256 && aesObject.getKey().length() != 32) {
            bindingResult.addError(new FieldError("aesObject" ,"key", "Длина ключа должна быть 32 символа для 256-битного ключа"));
            hasMistakes = true;
            model.addAttribute("keySizeError", true);
        }

        if(!aesObject.getIv().isBlank()){
            if (keySize == 128 && aesObject.getIv().length() != 16) {
                bindingResult.addError(new FieldError("aesObject" ,"iv", "Длина IV должна быть 16 символов для 128-битного ключа"));
                hasMistakes = true;
                model.addAttribute("ivSizeError", true);
            } else if (keySize == 192 && aesObject.getIv().length() != 24) {
                bindingResult.addError(new FieldError("aesObject" ,"iv", "Длина IV должна быть 24 символа для 192-битного ключа"));
                hasMistakes = true;
                model.addAttribute("ivSizeError", true);
            } else if (keySize == 256 && aesObject.getIv().length() != 32) {
                bindingResult.addError(new FieldError("aesObject" ,"iv", "Длина IV должна быть 32 символа для 256-битного ключа"));
                hasMistakes = true;
                model.addAttribute("ivSizeError", true);
            }
        }
        return hasMistakes;
    }
}
