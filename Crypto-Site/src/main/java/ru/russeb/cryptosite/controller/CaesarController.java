package ru.russeb.cryptosite.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.russeb.cryptosite.cipher.CaesarCipher;
import ru.russeb.cryptosite.model.Caesar;

//Контоллер запросов для шифрования Цезаря
@Controller
@RequestMapping(value = "/crypto/Caesar")
public class CaesarController {
    @GetMapping("")
    public String getCaesarPage(Model model) {
        model.addAttribute("caesarObject", new Caesar());
        return "caesar";
    }
    @PostMapping("/calculate")
    public String calculateCaesar(Model model, @ModelAttribute("caesarObject") Caesar caesarObject, BindingResult bindingResult){
        if(caesarObject.getTextToCipher().isBlank()){
            bindingResult.addError(new FieldError("caesarObject", "textToCipher", "Текст не может быть пустым"));
            model.addAttribute("textEmpty", true);
            return "caesar";
        }
        StringBuilder newText = new StringBuilder();
        if(caesarObject.isEncrypt()){
            newText.append(CaesarCipher.encryptText(caesarObject.getTextToCipher(),caesarObject.getShift()));
        }else{
            newText.append(CaesarCipher.decryptText(caesarObject.getTextToCipher(),caesarObject.getShift()));
        }
        model.addAttribute("result", newText);
        model.addAttribute("formSubmitted", true);
        return "caesar";
    }
}
