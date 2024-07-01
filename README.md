# crypto-site
Учебная практика - сайт на Java Spring Boot Web + Thymeleaf

<p style=" text-indent: 25px;">Разрабатываемое веб-приложение может быть полезно тем, кто хочет изучить историю развития шифрования и некоторые из алгоритмов с особенностями.</p>
<p style=" text-indent: 25px;">Для разработки данного сервиса выбрана технология Java Spring Boot Web, которая позволяет быстро и эффективно создавать веб-приложения на Java. Сервис будет использовать шаблонизатор Thymeleaf для отображения данных на веб-странице.</p>
<p style=" text-indent: 25px;">Разработанное веб-приложение предоставляет пользователям возможность шифрования и расшифрования текстовой информации с использованием различных алгоритмов шифрования.</p>
<p style=" text-indent: 25px;">
При запуске приложения, пользователю отображается главная страница, содержащая краткую история криптографии и кнопка, переносящая на выбор алгоритма шифрования. На сайте реализовано четыре алгоритма – шифр Цезаря, шифр AES, шифр RSA и шифр RC4.</p>
<img src="screenshots-site/main_page.png"></img>
<p style="text-align: center;">Главная страница</p>
<img src="screenshots-site/history.png">
<p style="text-align: center;">История шифрования</p>
<div style="text-align:center; width:100%">
<img src="screenshots-site/choose_alg.gif">
</div>

<p style="text-align: center;">Выбор алгоритма шифрования</p>
<p style=" text-indent: 25px;">При выборе шифра Цезаря пользователь должен ввести текст для кодирования/декодирования, сдвиг вправо по буквам и выбрать тип операции (Зашифровать/Расшифровать).</p>
<img src="screenshots-site/cipher_page.png"></img>
<p style="text-align: center;">Страница шифра Цезаря</p>
<p style=" text-indent: 25px;">В каждом агоритме шифрования необходимо заполнить все поля(если не указано обратное) и нажать на кнопку "Рассчитать".</p>
<img src="screenshots-site/plain_text.png"></img>
<p style="text-align: center;">Ввод текста, который должен быть закодирован</p>
<img src="screenshots-site/choose_shift.png"></img>
<p style="text-align: center;">Выбор сдвига</p>
<img src="screenshots-site/choose_operation.png"></img>
<p style="text-align: center;">Выбор операции</p>
<img src="screenshots-site/result.png"></img>
<p style="text-align: center;">Результат преобразования текста</p>
<img src="screenshots-site/decrypt_result.png"></img>
<p style="text-align: center;">Обратное действие - Расшифровать</p>
<p style=" text-indent: 25px;">При наличии ошибок при заполнении необходимых полей, а также при неудачной попытке расшифровать текста, будут выводиться ошибки.</p>
<img src="screenshots-site/validation.png"></img>
<p style="text-align: center;">Ошибка заполнения</p>
<img src="screenshots-site/error_decrypt.png"></img>
<p style="text-align: center;">Ошибка расшифровки</p>
<p style=" text-indent: 25px;">При выборе шифра AES пользователь должен ввести текст для кодирования/декодирования, размер ключа (128 бит, 192 бита, 256 бит), ключ, IV(необязательно) и выбрать тип операции (Зашифровать/Расшифровать).</p>
<p style=" text-indent: 25px;">При выборе шифра RC4 пользователь должен ввести текст для кодирования/декодирования, ключ и выбрать тип операции (Зашифровать/Расшифровать). </p>
<p style=" text-indent: 25px;">
При выборе шифра RSA пользователь должен ввести текст для кодирования/декодирования, размер ключа, открытый и закрытый ключи и выбрать тип операции (Зашифровать/Расшифровать).</p>
<p style=" text-indent: 25px;">Конец страниц отображает, что сайт был разработан в рамках учебной практики для Высшего Учебного Заведения СПБГУАП. </p>
<img src="screenshots-site/end_page.png"></img>
