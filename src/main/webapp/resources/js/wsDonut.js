function reRenderStand(p) {
    $('.STAND').find('tbody').empty();
    for (var i = 0; i < p.length; i++) {
        var productObject = p[i];
        $('.STAND').find('tbody').append(
            '<tr>' +
            '<td>' + productObject.category + '</td>' +
            '<td>' + productObject.name + '</td>' +
            '<td>' + productObject.description + '</td>' +
            '<td>' + productObject.price + '₽</td>' +
            '</tr>'
        )
    }
}
