const botaoProcurar = document.querySelector('[data-procurar]')

botaoProcurar.addEventListener('click', function (e) {
    const cep = document.getElementById('cep').value
    const rua = document.getElementById('rua')
    const bairro = document.getElementById('bairro')
    const cidade = document.getElementById('cidade')
    const uf = document.getElementById('uf')

    e.preventDefault()

    xhr = new XMLHttpRequest
    xhr.open('GET', `https://viacep.com.br/ws/${cep}/json`)

    xhr.addEventListener('load', function(){
        const info = xhr.responseText
        console.log(info, typeof info)

        const respAdd = JSON.parse(info)

        rua.value = respAdd.logradouro;
        bairro.value = respAdd.bairro;
        cidade.value = respAdd.localidade;
        uf.value = respAdd.uf;

        

    })

    xhr.send()

})
    


       
    
    


