package ru.russeb.cryptosite.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.russeb.cryptosite.cipher.Rc4Cipher;
import ru.russeb.cryptosite.model.Rc4;

import java.nio.charset.StandardCharsets;

//Контоллер запросов для шифрования RC4
@Controller
@RequestMapping(value = "/crypto/RC4")
public class Rc4Controller {
    @GetMapping("")
    public String getRC4Page(Model model) {
        model.addAttribute("rc4Object",new Rc4());
        return "rc4";
    }


    @PostMapping("/calculate")
    public String calculateRC4(Model model, @ModelAttribute("rc4Object") Rc4 rc4Object, BindingResult bindingResult) {

        if(hasMistakes(model,bindingResult,rc4Object)){
            return "rc4";
        }
        if(rc4Object.isEncrypt()){
            model.addAttribute("result", Rc4Cipher.byteArrayToHexString(Rc4Cipher.encrypt(rc4Object.getTextToCipher().getBytes(StandardCharsets.UTF_8),rc4Object.getKey().getBytes(StandardCharsets.UTF_8))));
        }else{
            try {
                model.addAttribute("result", new String(Rc4Cipher.decrypt(Rc4Cipher.hexStringToByteArray(rc4Object.getTextToCipher()),rc4Object.getKey().getBytes())));
            }catch (Exception e){
                model.addAttribute("decryptError",true);
            }
        }
        model.addAttribute("formSubmitted", true);
        return "rc4";
    }


    private boolean hasMistakes(Model model,BindingResult bindingResult, Rc4 rc4Object){
        boolean hasMistakes = false;
        if(rc4Object.getTextToCipher().isBlank()){
            hasMistakes = true;
            bindingResult.addError(new FieldError("rc4Object", "textToCipher", "Текст не может быть пустым"));
            model.addAttribute("textEmpty", true);
        }
        if(rc4Object.getKey().isBlank()){
            hasMistakes = true;
            bindingResult.addError(new FieldError("rc4Object", "key", "Ключ не может быть пустым"));
            model.addAttribute("keyEmpty", true);
        }

        return hasMistakes;
    }
}
