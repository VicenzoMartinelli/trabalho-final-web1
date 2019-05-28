"use strict";

$(() => {
    $('.dd-allianz .nav-link').click((ev) => {
        const element = $(ev.currentTarget).parent().find('.dd-allianz-content');

        if (element.hasClass('dd-active')) {
            element.removeClass('dd-active');
        } else {
            element.addClass('dd-active');
        }
    })
});