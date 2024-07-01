package ru.russeb.cryptosite.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ru.russeb.cryptosite.cipher.RsaCipher;
import ru.russeb.cryptosite.cipher.RsaKeyGenerator;
import ru.russeb.cryptosite.model.Rsa;
import ru.russeb.cryptosite.model.RsaKeys;

import java.nio.charset.StandardCharsets;

//Контоллер запросов для шифрования RSA
@Controller
@RequestMapping(value = "/crypto/RSA")
public class RsaController {
    @GetMapping("")
    public String getRsaPage(Model model) {
        model.addAttribute("rsaObject",new Rsa());
        return "rsa";
    }

    @PostMapping("/gen-keys")
    @ResponseBody
    public String generateKeys(@RequestParam(name = "keySize") int keySize) {
        RsaKeys rsaKeys = RsaKeyGenerator.get(keySize);

        // Создание JSON-объекта с ключами
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode jsonResponse = mapper.createObjectNode();
        jsonResponse.put("privateKey", rsaKeys.getPrivateKey());
        jsonResponse.put("publicKey", rsaKeys.getPublicKey());

        // Возвращаем JSON-объект в качестве ответа
        return jsonResponse.toString();
    }

    @PostMapping("/calculate")
    public String calculate(@ModelAttribute("rsaObject") Rsa rsaObject, BindingResult bindingResult,Model model) {
        if(hasMistakes(rsaObject,model,bindingResult)){
            return "rsa";
        }
        if(rsaObject.isEncrypt()){
            model.addAttribute("result",RsaCipher.encrypt(rsaObject.getTextToCipher(),rsaObject.getPublicKey()));
        }else{
            try{
                model.addAttribute("result",RsaCipher.decrypt(rsaObject.getTextToCipher(),rsaObject.getPrivateKey()));

            } catch (Exception e) {
                model.addAttribute("decryptError",true);
                return "rsa";
            }
        }
        model.addAttribute("formSubmitted", true);
        return "rsa";
    }

    private boolean hasMistakes(Rsa rsaObject, Model model, BindingResult bindingResult) {
        int keySize = rsaObject.getKeysSize();
        boolean hasMistakes = false;
        if(sizeTextToMuch(keySize,rsaObject.getTextToCipher())){
            hasMistakes = true;
            bindingResult.addError(new FieldError("rsaObject", "textToCipher", "Размер текста слишком большой"));
            model.addAttribute("textToCipherError",true);
        }
        if(rsaObject.getTextToCipher().isBlank()){
            hasMistakes = true;
            bindingResult.addError(new FieldError("rsaObject", "textToCipher", "Текст не может быть пустым"));
            model.addAttribute("textToCipherError",true);
        }

        if(keySize < 512){
            bindingResult.addError(new FieldError("rsaObject", "keysSize", "Размер ключа должен быть больше или равен 512"));
            hasMistakes = true;
            model.addAttribute("keysSizeError",true);
        }if(keySize > 9999){
            bindingResult.addError(new FieldError("rsaObject", "keysSize", "Размер ключа должен быть меньше 9999"));
            hasMistakes = true;
            model.addAttribute("keysSizeError",true);
        }

        if(rsaObject.getPublicKey().length()<128){
            hasMistakes = true;
            bindingResult.addError(new FieldError("rsaObject", "publicKey", "Количество символов в ключе не должно быть меньше 128"));
            model.addAttribute("publicKeyError",true);
        }
        if(rsaObject.getPrivateKey().length()<128){
            hasMistakes = true;
            bindingResult.addError(new FieldError("rsaObject", "privateKey", "Количество символов в ключе не должно быть меньше 128"));
            model.addAttribute("privateKeyError",true);
        }

        return hasMistakes;
    }

    private boolean sizeTextToMuch(int keySize,String textToCipher){
        int sizeBlock = 11;
        int countBytes = keySize / 8 - sizeBlock;
        int countBytesText = textToCipher.getBytes(StandardCharsets.UTF_8).length;
        return countBytesText>countBytes;
    }
}
