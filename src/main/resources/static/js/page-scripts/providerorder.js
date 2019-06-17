"use strict";

const order = {
    id: null,
    delivered: false,
    orderDate: '',
    provider: {},
    description: '',
    orderItems: []
};

const editingItem = {
    id: null,
    count: 0,
    value: 0,
    product: {}
};

const templateItemElement = (obj) =>
`<div class="card text-white bg-dark pl-2 product-item">
    <input type="hidden" value="${obj.product.id}">
    <p class="p-0">${obj.product.name}</p>
    
    <div>
        <span>Unidades: ${obj.count}</span><br>
        <span>Valor Unitário: R$ ${obj.value}</span><br>
        <span>Total Produto: R$ ${obj.value * obj.count}</span>
    </div>
    <a href="'javascript:openProductOrderItem(true, this);'"
       class="text-white edit">
        <i class="far fa-edit fa-lg"></i>
    </a>
    
    <a href="'javascript:deleteItem(true, this);'"
       class="text-white delete">
        <i class="far fa-trash-alt fa-lg"></i>
    </a>
</div>`;

$(document).ready(function () {
    $(".add-product-btn").click(() => openProductOrderItem(false, this));
});

function saveProviderOrder() {
    if (!($('#frm-order').valid())) {
        swal({
            title: 'Erro!',
            text: 'Verifique as informações do seu formulário!',
            type: 'error',
            showConfirmButton: false,
            timer: 1500
        });
        return;
    }

    $.ajax({
        type: $('#frm-order').attr('method'),
        url: $('#frm-order').attr('action'),
        data: getCleanFormSerialized('#frm'),
        success: (e) => {
            swal({
                title: 'Salvo!',
                text: 'Registro Salvo com Sucesso!',
                type: 'success'
            }, () => {
                window.location = '/providerorder';
            });

        },
        error: (e) => {
            swal('Errou!', 'Falha ao salvaro registro!', 'error');
        }
    })
}

function openProductOrderItem(editing, element) {
    if(editing) {

    }else{
        onOpenModal();
    }
    $("#frm").modal();
}

function saveProductOrderItem() {
    let { id } = editingItem.product;

    if(id && id > 0)
    {

    }
    else{
        editingItem.product.id   = $("#product").val();
        editingItem.product.name = $("#product option:selected").text();
        editingItem.count        = $("#count").val();
        editingItem.value        = $("#value").val();
        order.orderItems.push(editingItem);
        $("#products-container").append(`<div class="col-lg-4">${templateItemElement(editingItem)}</div>`)
    }

    swal({
        title: 'Salvo!',
        text: 'Produto Salvo com Sucesso!',
        type: 'success'
    }, () => $("frm").modal('hide'));
}