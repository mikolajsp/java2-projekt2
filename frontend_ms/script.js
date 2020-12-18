function execute(){

    document.getElementById("response").innerHTML = "";

    var town = document.getElementById("itown").value;
    var name = document.getElementById("iname").value;
    var type = document.getElementById("itype").value;
    var url = "http://localhost:8080/search?";

    if(town){
        var key = encodeURIComponent("town");
        var value = encodeURIComponent(town);
        url += key + "=" + value + "&"
    }
    if(name){
        var key = encodeURIComponent("name");
        var value = encodeURIComponent(name);
        url += key + "=" + value + "&"
    }
    if(type){
        var key = encodeURIComponent("type");
        var value = encodeURIComponent(type);
        url += key + "=" + value + "&"
    }
    

    var xmlHttp = new XMLHttpRequest();

    xmlHttp.open("GET", url, false);
    xmlHttp.send(null);
    
    
    var content = JSON.parse(xmlHttp.responseText);

    console.log(content);

    for (var school in content){
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


}