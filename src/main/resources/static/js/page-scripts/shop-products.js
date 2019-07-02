const templateProduct = (p) => `
<div class="col-lg-4 p-5">
    <a href="/product/details/${p.id}">
        <div class="card text-dark">
            <div class="card-body">
                <div>
                    <img src="/product/findImage/${p.urlsImgs && p.urlsImgs[0]}" />
                </div>
            </div>
            <div class="card-footer">
                <span>
                    ${p.name} - ${p.createdDate.split('/').pop()}
                    <br />
                    <div class="d-inline-block">
                        <i class="fa fa-star"></i>
                        <i class="fa fa-star"></i>
                        <i class="fa fa-star"></i>
                        <i class="fa fa-star"></i>
                        <i class="fa fa-star"></i>
                    </div>
                    <br />R$ ${String(p.value).replace(".", ",")}
                    <br />Ou <b>10x</b> de R$<b>${String((p.value / 10).toFixed(2)).replace(".", ",")}</b>
                </span>
            </div>
        </div>
    </a>
</div>`;

$(() => {
    $.getJSON('/product/findproducts/4', (r) => {
        debugger;

        r.products.content.forEach(x => {
            $('#product-list').append(templateProduct(x));
        });
    });
});