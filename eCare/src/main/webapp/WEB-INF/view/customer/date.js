$(document).ready(function () {
    const d = new Date();
    let day = d.getDate();
    let month = d.getMonth() + 1;
    const year = d.getFullYear()-14;
    if(day<10)
    {
        day='0'+day;
    }
    if(month<10)
    {
        month='0'+month;
    }
    let maxDate = year + "-" + month + "-" + day;
    $("#date").attr({
        "max": maxDate
    })
});