function readURL(input) {

    if (input.files && input.files[0]) {

        var reader = new FileReader();

        reader.onload = function (e) {
            $('#img-preview').attr('src', e.target.result);
            $('#image-input').parent().find('label').text(input.files[0].name);
        };

        reader.readAsDataURL(input.files[0]);
    }
}

$("#image-input").change((e) => {
    readURL(e.target);
});