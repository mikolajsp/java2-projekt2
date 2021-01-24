$('#goCompare').click(function() {
    var schoolToCompare = document.getElementsByClassName('active');
    console.log(schoolToCompare);
    var str = "";
    for (var i = 0; i < schoolToCompare.length; i++) {
        str += schoolToCompare[i].id.slice(13) + "&";
    }
    $(this).attr("href", "compare.html?" + str);
});