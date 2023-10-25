# PassWord Storage 🔒🗄️:

## Project Info
- Programing Language 📕    -> Java ☕
- Author                    -> RiseGhost 👻
- Android version           -> 9.0 or +

## Introdução:
Este projeto tem como objetivo a criação de um aplicativo para a gestão de Palavras passes. Como tal esse app conta com criptografia AES na senhas salvadas. As senhas são armazenadas numa base de dados utilizando o Room Database.

## Features ✅:
- Criptografia AES na senhas
- Categorizar as senhas
- Filtar senhas por categoria
- Login com biometria 👆🏻
- Lock PassWords with Biometric 👆🏻🔒
- ...

## Como a feita a criptografia 🔐:
Quando o utilizador instala o app pela primeira é lhe pedido um pin númerico que será utilizado para efetuar o login na app. O hash desse pin é armazenado nos __shared preferences__ do app. Após isso é gerada uma __chave privada__ e um __inicial vector (iv)__ de 8 bits que ficam armazenados no __shared preferences__ do app.

## Modelação do Objecto PassWord:
O objeto password é o objeto utilizado para fazer a modelação das senhas do usuário e é o objeto que será escrito na base de dados. Como tal tem de ter uma modelação que premita a implementação das funcionalidades pretendidas.

<image src="https://private-user-images.githubusercontent.com/91985039/276467276-c4c68331-f14b-4f39-a4db-dec49243d09d.png?jwt=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJnaXRodWIuY29tIiwiYXVkIjoicmF3LmdpdGh1YnVzZXJjb250ZW50LmNvbSIsImtleSI6ImtleTEiLCJleHAiOjE2OTc2ODQ0ODMsIm5iZiI6MTY5NzY4NDE4MywicGF0aCI6Ii85MTk4NTAzOS8yNzY0NjcyNzYtYzRjNjgzMzEtZjE0Yi00ZjM5LWE0ZGItZGVjNDkyNDNkMDlkLnBuZz9YLUFtei1BbGdvcml0aG09QVdTNC1ITUFDLVNIQTI1NiZYLUFtei1DcmVkZW50aWFsPUFLSUFJV05KWUFYNENTVkVINTNBJTJGMjAyMzEwMTklMkZ1cy1lYXN0LTElMkZzMyUyRmF3czRfcmVxdWVzdCZYLUFtei1EYXRlPTIwMjMxMDE5VDAyNTYyM1omWC1BbXotRXhwaXJlcz0zMDAmWC1BbXotU2lnbmF0dXJlPTcwYTFlNTA0ZWM1OTIxN2NiOGEzMzc3ZjZlY2U5MDkyMjE3YmNlYjQ2NmQwYTkwZmMxMmY3YzEyMmJiYjkwYWUmWC1BbXotU2lnbmVkSGVhZGVycz1ob3N0JmFjdG9yX2lkPTAma2V5X2lkPTAmcmVwb19pZD0wIn0.ybmwfQ8bs1LDe9JLti0ZXyIUz2aCGEqrxQklIa4NwKw">
