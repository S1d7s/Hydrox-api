
// Instale primeiro: npm install dotenv
require('dotenv').config();

const apiKey = process.env.API_KEY;
console.log('Chave carregada:', apiKey);

// Exemplo de uso
fetch('https://api.exemplo.com/dados', {
  headers: {
    'Authorization': `Bearer ${apiKey}`
  }
});
