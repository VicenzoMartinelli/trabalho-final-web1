function templateRow(scImg, nome, quantidade, valor, qtdeMaxima, id) {
    return `
                <tr id="${id}" >
            <td>
            <div class="container-fluid d-inline-flex px-0" name="cNome">
                <img name="imgProduto" src="${scImg}"
            alt="Imagem do Produto" style="width: 33%; height: 90px; border-radius: 1rem; margin-right: 3%;" />
                ${nome}
                </div>
                </td>
                <td name="quantidadeIni">
                <input type="number" onchange="updateTotal();" class="form-control" min="1" max="${qtdeMaxima}" onfocus="onEnter(this)" onblur="onExit(this)" value="${quantidade}" />
                </td>
                <td name="valorIni"> <span>${valor}</span></td>
                <td><a onclick="deleteRow(this, event);"><i class="far fa-window-close fa-lg text-dark h-100" title="Excluir produto" data-toggle="tooltip"></i></a></td>
            </tr>`;
}

function newRow(linha) {
    $('#products-checkout tbody').append(linha);
}

function updateTotal() {
    let frete = 35;
    let subtotal = 0;
    $('#products-checkout tbody tr').each(function (index, element) {
        let qtde = Number($(element).children('td[name="quantidadeIni"]').children('input').val());
        let valor = Number($(element).children('td[name="valorIni"]').children('span').text());
        subtotal += (qtde * valor);
    });

    $('#subTotal').text(subtotal.toFixed(2));
    $('#totalfrete').text(frete.toFixed(2));
    $('#total').text((subtotal + frete).toFixed(2));

    updateStorage();
}

function updateStorage() {
    let items = [];
    $('#products-checkout tbody tr').each(function (index, element) {
        items.push({
            id: $(element).prop('id'),
            scImg: $(element).find('img[name="imgProduto"]').attr('src'),
            valor: Number($(element).children('td[name="valorIni"]').children('span').text()),
            nome: $(element).find('div[name="cNome"]').text(),
            quantidade: Number($(element).children('td[name="quantidadeIni"]').children('input').val()),
            qtdeMax: Number($(element).children('td[name="quantidadeIni"]').children('input').prop('max'))
        });
    });

    setArrayStorage(items);
}

function deleteRow(element, ev) {
    ev.preventDefault();
    let tr = $(element).parents('tr');
    $(tr).css("transiction", 'all .3s linear');
    $(tr).css("opacity", 0);
    setTimeout(() => {
        $(tr).remove();
        updateTotal();
    }, 300);
    clearTimeout();
}

function saveOrder(paymentType){
    debugger;
    const order = {
        paymentType: paymentType,
        orderItems: getArrayStorage().map((it) => (
            {
                count: it.quantidade,
                product: {
                    id: it.id
                }
            }))
    };
    $.ajax({
        type: 'POST',
        url: '/order/saveorder',
        contentType : 'application/json',
        data : JSON.stringify(order),
        success: (e) => {
            swal({
                title: 'Salvo!',
                text: 'Pedido efetuado com sucesso!',
                type: 'success'
            }, () => {
                localStorage.setItem('arrDataCart', JSON.stringify([]));
                window.location = '/';
            });

        },
        error: (e) => {
            swal('Errou!', 'Falha ao salvaro registro!', 'error');
        }
    })
}
$(() => {
    $('#floating-cart').hide();
    $('#creditCard').click(() => {
        $('#divCreditCard').show();
        $('#divPayPal').hide();
    });

    $('#paypal').click(() => {
        $('#divCreditCard').hide();
        $('#divPayPal').show();
    });

    $('#creditCard').click();

    let items = getArrayStorage();

    items.forEach((x) => {
        newRow(templateRow(x.scImg, x.nome, x.quantidade, x.valor, x.qtdeMax, x.id));
    });
});