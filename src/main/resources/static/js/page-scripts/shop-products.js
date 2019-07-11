let currentPage;
let isLast;
const templateProduct = (p) => `
<div class="col-lg-4 p-5">
    <a href="/product/shop/details/${p.id}">
        <div class="card text-dark">
            <div class="card-body">
                <div>
                    <img src="/product/findImage/${p.urlsImgs.length === 0 ? "null.gif" : p.urlsImgs[0]}" />
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

function loadData(){
    if(isLast)
        return;
    let category = $('#category-id').val();
    showLoading();
    $.ajax({
        url: `/product/findproducts`,
        dataType: 'json',
        data: {
            page: (currentPage === undefined ? 1 : currentPage + 1),
            categoryId: category,
            filterText: $('#filterText').val()
        },
        success: (r) => {
            r.products.content.forEach(x => {
                $('#product-list').append(templateProduct(x));
            });

            currentPage = r.products.pageable.pageNumber + 1;
            isLast = r.products.last;

            hideLoading();
        }
    });
}

$(() => {
    loadData();

    $(window).scroll(function() {
        let height = document.documentElement.offsetHeight,
        offset = document.documentElement.scrollTop + window.innerHeight;

        if ((offset + 70) >= height) {
            loadData();
        }
    });

    $('#search-btn').click(() => {
        $('#product-list').children().remove();
        currentPage = undefined;
        isLast = false;
        loadData();
    });
});