var mymap = L.map('mapid', {
    preferCanvas: true
}).setView([52, 18], 6);
L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
    attribution: '&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
}).addTo(mymap);
var markers = L.layerGroup();

function execute(){

    document.getElementById("response").innerHTML = "";

    markers.clearLayers();


   
    var town = document.getElementById("itown").value;
    var name = document.getElementById("iname").value;
    var type = document.getElementById("itype").value;
    var studlower = document.getElementById("studlower").value;
    var studupper = document.getElementById("studupper").value;
    var branlower = document.getElementById("branlower").value;
    var branupper = document.getElementById("branupper").value;
    var voivodeship = document.getElementById("voivodeship").value;
    var county = document.getElementById("county").value;
    var community = document.getElementById("community").value;
    var origin = document.getElementById("origin").value;
    var distance = document.getElementById("distance").value;


    var url = "http://localhost:8080/search?";

    if(town){
        var key = encodeURIComponent("town");
        var value = encodeURIComponent(town);
        url += key + "=" + value + "&";
    }
    if(name){
        var key = encodeURIComponent("name");
        var value = encodeURIComponent(name);
        url += key + "=" + value + "&";
    }
    if(type){
        if(type == "Dowolna"){}
        else{
        var key = encodeURIComponent("type");
        var value = encodeURIComponent(type);
        url += key + "=" + value + "&";
        }
    }
    if(studlower || studupper){
        var key1 = encodeURIComponent("studlower");
        var value1 = encodeURIComponent(studlower);
        var key2 = encodeURIComponent("studupper");
        var value2 = encodeURIComponent(studupper);
        url += key1 + "=" + value1 +"&" + key2 + "=" + value2 +"&";
    }
    if(branlower || branupper){
        var key1 = encodeURIComponent("branlower");
        var value1 = encodeURIComponent(branlower);
        var key2 = encodeURIComponent("branupper");
        var value2 = encodeURIComponent(branupper);
        url += key1 + "=" + value1 +"&" + key2 + "=" + value2 +"&";
    }
    if(voivodeship){
        if(voivodeship=="Dowolne"){}
        else{
            var key = encodeURIComponent("voivodeship");
            var value = encodeURIComponent(voivodeship);
            url += key + "=" + value + "&";
        }
    }
    if(county){
        var key = encodeURIComponent("county");
        var value = encodeURIComponent(county);
        url += key + "=" + value + "&";
    }
    if(community){
        var key = encodeURIComponent("community");
        var value = encodeURIComponent(community);
        url += key + "=" + value + "&";
    }
    if (distance){
        var key1 = encodeURIComponent("distance");
        var value1 = encodeURIComponent(distance);
        var key2 = encodeURIComponent("origin");
        var value2 = encodeURIComponent(origin);
        url += key1 + "=" + value1 +"&" + key2 + "=" + value2 +"&";
    }
    

    var xmlHttp = new XMLHttpRequest();

    xmlHttp.open("GET", url, false);
    xmlHttp.send(null);
    
    
    var content = JSON.parse(xmlHttp.responseText);
    
    console.log(content);

    for (var school in content){
        var marker = L.circleMarker([content[school].lat, content[school].lon], {
            radius: 7
        }).addTo(markers);
        var newElement = document.createElement('div');
        newElement.id = content[school].id;
        newElement.className = "school";
        newElement.innerHTML = 
        "Typ: " + content[school].type + "<br>" +
        "Nazwa: " + content[school].name + "<br>" +
        "Województwo: " + content[school].voivodeship + "<br>" +
        "Powiat: " + content[school].county + "<br>" +
        "Gmina: " + content[school].community + "<br>" +
        "Liczba oddziałów: " + content[school].branches + "<br>" +
        "Liczba uczniów: " + content[school].voivodeship + "<br>" +
        "Email: " + content[school].email + "<br>" +
        "Telefon: " + content[school].phoneNumber + "<br>" +
        "Strona internetowa: <a href=\"" + content[school].website + "\">" + content[school].website + "</a>" + "<br>" +
        "Adres: " + content[school].street + " " + content[school].houseNumber + ", " + content[school].town + "<br><br>";

        document.getElementById("response").appendChild(newElement);
    }

    mymap.addLayer(markers);

}