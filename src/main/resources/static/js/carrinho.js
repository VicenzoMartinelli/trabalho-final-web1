$(() => {
    $('#c-ok').hide();
    $('#btnAddCarrinho').click((e) => {
        e.preventDefault();
        adicionarLinha();
        $('#c-ok').show();
        $('#c-vazio').hide();
        recalculaTotal();
        alert('Adicionado ao carrinho!');
    });
    let items = getArrayStorage();

    items.forEach((x) => {
        newRowCarrinho(criarLinha(x.scImg, x.nome, x.quantidade, x.valor));
    });
    recalculaTotal();

});

function newProduct() {
    let valor = $('#preco').text();
    let scImg = $('#imagem-principal').attr('src').replace('img/', 'img/sm-');
    let quantidade = $('#quantidade').val();
    let nome = $('#nomeProduto').text();
    let tr = criarLinha(scImg, nome, quantidade, valor);

    return {
        html: tr
    };
}

function criarLinha(scImg, nome, quantidade, valor) {
    return `
    <tr>
      <td> 
        <div class="container-fluid d-inline-flex align-items-center px-0" name="cNome">
            <img name="imgProduto" src="${scImg}" 
                    alt="Imagem do Produto" style="width: 20%;" />
            ${nome}
        </div>
      </td>
      <td name="quantidadeIni">
        <input type="number" onchange="recalculaTotal();" class="form-control" min="1" onfocus="onEnter(this)" onblur="onExit(this)" value="${quantidade}"></input>
      </td>
      <td name="valorIni"> <span>${valor}</span> R$</td>
      <td><a onclick="deletarItem(this, event);"> <i class="fa fa-2x fa-times-rectangle text-dark"></i></a></td>
    </tr>`;
}

function newRowCarrinho(linha) {
    if ($('#tblProdutos tbody') == 0)
        $('#tblProdutos').append('<tbody> </tbody>');


    $('#tblProdutos tbody').append(linha);
}

function adicionarLinha() {
    let r = newProduct();

    newRowCarrinho(r.html);
    atualizaStorage();
}

function deletarItem(element, evento) {
    evento.preventDefault();
    let tr = $(element).parents('tr');
    $(tr).css("transiction", 'all .3s linear');
    $(tr).css("opacity", 0);
    setTimeout(() => {
        $(tr).remove();
        recalculaTotal();
    }, 300);
    clearTimeout();

}

function recalculaTotal() {
    let frete = Number($('#valorFrete').text());
    let subtotal = 0;
    $('#tblProdutos tbody tr').each(function (index, element) {
        let qtde = Number($(element).children('td[name="quantidadeIni"]').children('input').val());
        let valor = Number($(element).children('td[name="valorIni"]').children('span').text());
        subtotal += (qtde * valor);
    });

    $('#subTotal').text(subtotal.toFixed(2));
    $('#totalfrete').text(frete.toFixed(2));
    $('#total').text((subtotal + frete).toFixed(2));

    if ($('#tblProdutos tbody tr').length == 0) {
        $('#c-vazio').show();
        $('#c-ok').hide();
    } else {
        $('#c-vazio').hide();
        $('#c-ok').show();
    }

    atualizaStorage();
}

function atualizaStorage() {
    let items = [];
    $('#tblProdutos tbody tr').each(function (index, element) {
        debugger;
        items.push({
            scImg: $(element).find('img[name="imgProduto"]').attr('src'),
            valor: Number($(element).children('td[name="valorIni"]').children('span').text()),
            nome: $(element).find('div[name="cNome"]').text(),
            quantidade: Number($(element).children('td[name="quantidadeIni"]').children('input').val())
        });
    });

    setArrayStorage(items);
}

function showFrete() {
    if ($('#txtFrete').val() != 0)
        $('#entrega').show();
}

function getArrayStorage() {
    return JSON.parse(localStorage.getItem('arrDataCart')) || [];
}

function setArrayStorage(arr) {
    localStorage.setItem('arrDataCart', JSON.stringify(arr));
}