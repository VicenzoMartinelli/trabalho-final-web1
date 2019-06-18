"use strict";

const order = {
    id: null,
    delivered: false,
    orderDate: '',
    provider: {},
    description: '',
    orderItems: []
};

let editingItem = {
    id: null,
    count: 0,
    value: 0,
    product: {}
};

const templateItemElement = (obj) =>
`<div class="card text-white bg-dark pl-2 product-item">
    <input type="hidden" data-product value="${obj.product.id}">
    <p class="p-0">${obj.product.name}</p>
    
    <div>
        <span>Unidades: ${obj.count}</span><br>
        <span>Valor Unitário: R$ ${obj.value}</span><br>
        <span>Total Produto: R$ ${obj.value * obj.count}</span>
    </div>
    <a class="text-white edit" onclick="openProductOrderItem(true, this);">
        <i class="far fa-edit fa-lg"></i>
    </a>
    
    <a class="text-white delete">
        <i class="far fa-trash-alt fa-lg"></i>
    </a>
</div>`;

$(document).ready(function () {
    $(".add-product-btn").click(() => openProductOrderItem(false, this));
    $(".edit").click(function(ev) {
        openProductOrderItem(true, this);
    });
    $(".delete").click(function(ev) {
        deleteProductOrderItem(this);
    });
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
        debugger;
        let id = $(element)
            .parents('.col-lg-4')
            .find('[data-product]').val();


        editingItem = order.orderItems.filter((x) => x.product.id === id)[0];
        let { value, count, product } = editingItem;
        $("#product").val(product.id).focus();
        $("#count").val(count);
        $("#value").val(value);
    }else{
        onOpenModal();
    }
    $("#frm").modal();
}

function saveProductOrderItem() {
    let { id } = editingItem.product;

    editingItem.product.id   = $("#product").val();
    editingItem.product.name = $("#product option:selected").text();
    editingItem.count        = $("#count").val();
    editingItem.value        = $("#value").val();

    if(id && id > 0)
    {
        order.orderItems[order.orderItems.findIndex((x) => x.product.id == id)] = editingItem;
        $("input[data-product]")
            .find(`[value='${id}']`)
            .prop('outerHTML', templateItemElement(editingItem));
    }
    else{
        order.orderItems.push(editingItem);
        $("#products-container")
            .append(`<div class="col-lg-4">${templateItemElement(editingItem)}</div>`)

    }
    editingItem = null;

    swal({
        title: 'Salvo!',
        text: 'Produto Salvo com Sucesso!',
        type: 'success'
    }, () => $("frm").modal('hide'));
}

function deleteProductOrderItem(element) {

}