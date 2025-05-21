
const userDTO = {
    email: "hello unhappy",
    password: "HelloUnhappy",
    state: "Active"
}

const bitacoraDTO = {
    idUsuario: 0,
    fechaYHora: "2023-10-10T10:00:00Z", 
    accion: "Login"
}

const CryptoJS = require('crypto-js');
const encrypt = (data)=>{
const secret_key = 'HelloUnhappyReoN';
const iv = 'HelloUnhappyReoN';
  const encrypted = CryptoJS.AES.encrypt(
    JSON.stringify(data),
    CryptoJS.enc.Utf8.parse(secret_key),
    {
      iv: CryptoJS.enc.Utf8.parse(iv),
      mode: CryptoJS.mode.CBC,
      padding: CryptoJS.pad.Pkcs7
    }
  );

  // Devuelve la cadena en base64
  const chain =  encrypted.toString(); 
    return chain.replace('/','_');
}

const decrypt = (data)=>{
const secret_key = 'HelloUnhappyReoN';
const iv = 'HelloUnhappyReoN';

  const decrypted = CryptoJS.AES.decrypt(
    data,
    CryptoJS.enc.Utf8.parse(secret_key),
    {
      iv: CryptoJS.enc.Utf8.parse(iv),
      mode: CryptoJS.mode.CBC,
      padding: CryptoJS.pad.Pkcs7
    }
  );

  const decryptedData = JSON.parse(decrypted.toString(CryptoJS.enc.Utf8));
  return decryptedData;
}

const sendData = async data => {
    const response = await fetch('http://localhost:8080/api/auth/user/', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    });
    const result = await response.json();
    return result;
}
const getData = async () => {
    const response = await fetch('http://localhost:8080/api/auth/user/', {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json'
        }
    });
    const result = await response.json();
    return result;
}

const getOne = async id => {
    const response = await fetch(`http://localhost:8080/api/auth/user/${encrypt(id)}`,
        {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json'
            }
        }
    ).then(
        res => {
            return res.json()
        }
    )
    .catch(
        err => {
            return {
                message: `${err}`
            }
        }
    )

    const result = response
    
    return result
}

const postBitacora = async data => {
    const response = await fetch('http://localhost:8081/api/bitacora/', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(encrypt(data))
    }).then(
        res => {
            return res.json()
        }
    ).catch(
        err => {
            return {
                message: `${err}`
            }
        }
    )
    const result = await response;
    return result;
}

const getBitacora = async () => {
    const response = await fetch('http://localhost:8081/api/bitacora/', {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json'
        }
    }).then(
        res => {
            return res.json()
        }
    ).catch(
        err => {
            return {
                message: `${err}`
            }
        }
    )
    const result = await response;
    return result;
}


getData().then(
    res => {
        console.log(decrypt(res.data))
    }
).catch(
    err => {
        console.log(err)
    }
)
