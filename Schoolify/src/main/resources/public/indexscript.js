var DOMAIN = "http://localhost:8080";


var COMPARE = [];


var mymap = L.map('mapid', {
    preferCanvas: true
}).setView([52, 19], 6);
L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
    attribution: '&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
}).addTo(mymap);
var markers = L.layerGroup();


function createRequestURI() {

    var town = document.getElementById("itown").value;
    var name = document.getElementById("iname").value;
    var type = document.getElementById("itype").value;
    // var studlower = document.getElementById("studlower").value;
    // var studupper = document.getElementById("studupper").value;
    // var branlower = document.getElementById("branlower").value;
    // var branupper = document.getElementById("branupper").value;
    var voivodeship = document.getElementById("voivodeship").value;
    var county = document.getElementById("county").value;
    var community = document.getElementById("community").value;
    var origin = document.getElementById("origin").value;
    var distance = document.getElementById("distance").value;

    var base = DOMAIN + "/search?";

    if (town) {
        var key = encodeURIComponent("town");
        var value = encodeURIComponent(town);
        base += key + "=" + value + "&";
    }
    if (name) {
        var key = encodeURIComponent("name");
        var value = encodeURIComponent(name);
        base += key + "=" + value + "&";
    }
    if (type) {
        if (type == "Dowolna") {} else {
            var key = encodeURIComponent("type");
            var value = encodeURIComponent(type);
            base += key + "=" + value + "&";
        }
    }
    // if (studlower || studupper) {
    //     var key1 = encodeURIComponent("studlower");
    //     var value1 = encodeURIComponent(studlower);
    //     var key2 = encodeURIComponent("studupper");
    //     var value2 = encodeURIComponent(studupper);
    //     base += key1 + "=" + value1 + "&" + key2 + "=" + value2 + "&";
    // }
    // if (branlower || branupper) {
    //     var key1 = encodeURIComponent("branlower");
    //     var value1 = encodeURIComponent(branlower);
    //     var key2 = encodeURIComponent("branupper");
    //     var value2 = encodeURIComponent(branupper);
    //     base += key1 + "=" + value1 + "&" + key2 + "=" + value2 + "&";
    // }
    if (voivodeship) {
        if (voivodeship == "Dowolne") {} else {
            var key = encodeURIComponent("voivodeship");
            var value = encodeURIComponent(voivodeship);
            base += key + "=" + value + "&";
        }
    }
    if (county) {
        var key = encodeURIComponent("county");
        var value = encodeURIComponent(county);
        base += key + "=" + value + "&";
    }
    if (community) {
        var key = encodeURIComponent("community");
        var value = encodeURIComponent(community);
        base += key + "=" + value + "&";
    }
    if (distance) {
        var key1 = encodeURIComponent("distance");
        var value1 = encodeURIComponent(distance);
        var key2 = encodeURIComponent("origin");
        var value2 = encodeURIComponent(origin);
        base += key1 + "=" + value1 + "&" + key2 + "=" + value2 + "&";
        setOriginMarker = true;
    }

    return base;
}

function requestSchoolList(url) {
    var xmlHttp = new XMLHttpRequest();
    url = createRequestURI();
    xmlHttp.open("GET", url, false);
    xmlHttp.send(null);
    var content = JSON.parse(xmlHttp.responseText);
    return content;
}

function createSchoolDescription(school) {
    return "Typ: " + school.type + "<br>" +
        "Nazwa: " + school.name + "<br>" +
        "Województwo: " + school.voivodeship + "<br>" +
        "Powiat: " + school.county + "<br>" +
        "Gmina: " + school.community + "<br>" +
        "Liczba oddziałów: " + school.branches + "<br>" +
        "Liczba uczniów: " + school.students + "<br>" +
        "Email: " + school.email + "<br>" +
        "Telefon: " + school.phoneNumber + "<br>" +
        "Strona internetowa: <a href=\"https://" + school.website + "\">" + school.website + "</a>" + "<br>" +
        "Adres: " + school.street + " " + school.houseNumber + ", " + school.town + "<br><br>";
}




function showGreen(id) {
    var toCompare = document.getElementById(`buttonCompare${id}`);
    var greenShow = document.createElement("img");
    greenShow.src = "greenTick.png";
    greenShow.width = 30;
    greenShow.heigth = 30;
    toCompare.appendChild(greenShow);
    COMPARE.push(id);
    toCompare.disabled = true;
}

function createSchoolDescriptionBasic(school) {
    return "Nazwa: " + school.name + "<br>" +
        "<a href =\"schoolpage.html?schoolid=" + school.id + "\">Więcej >> </a>";
}

function generateText() {
    return "Najbardziej popularne wśród użykowników";
}

function fillResponseDiv(schoolArray) {
    var intro = document.createElement('h4');
    intro.id = 0;
    intro.innerHTML = generateText();
    resp = document.getElementById("response");
    resp.appendChild(intro);
    var i = 1;
    for (var school in schoolArray) {
        var newElement = document.createElement('div');
        newElement.id = schoolArray[school].id;
        newElement.className = "school well";
        newElement.innerHTML = generateSchoolDivOnlyBasic(schoolArray[school], i);
        resp.appendChild(newElement);
        i++;
    }
    resp.style.paddingTop = "50px";

}


function makeMore(id) {
    var more = document.createElement("a");
    more.classList.add("btn");
    more.classList.add("btn-outline-primary");
    more.role = "button";
    more.href = `schoolpage.html?schoolid=` + id;
    more.innerHTML = "Więcej";
    return more;
}

function makeComp(id) {
    var comp = document.createElement("button");
    comp.classList.add("compareButton");
    comp.classList.add("btn");
    comp.classList.add("btn-outline-primary");
    comp.classList.add("btn-md");
    comp.id = `buttonCompare${id}`;
    comp.addEventListener('click', function() {
        showGreen(id);
    });
    comp.onclick = "showGreen(this.id)";
    comp.innerHTML = "Dodaj do porównania";
    return comp;
}

function placeMarkersOnMap(content) {
    var sL = content.schoolList;
    sL.forEach(element => {
        var marker = L.circleMarker([element.lat, element.lon], {
            radius: 7
        }).addTo(markers);
        var popupDIV = document.createElement("div");
        popupDIV.innerHTML = createSchoolDescription(element);
        popupDIV.appendChild(makeMore(element.id));
        popupDIV.appendChild(makeComp(element.id));
        marker.bindPopup(popupDIV);
    });
}





function generateSchoolDiv(school) {
    return "<div class=\"row\">" +
        "<div class=\"col-xs-6 col-sm-9 col-md-9 col-lg-10 title\">" +
        "<h3>" + school.name + "</h3>" +
        "<p>" + createSchoolDescription(school) + "</p>" +
        "</div>" +
        "</div>";

}

function generateSchoolDivOnlyBasic(school, i) {
    return "<div class=\"row\">" +
        "<div class=\"col-xs-6 col-sm-9 col-md-9 col-lg-10 title\">" +
        "<p style=\"font-size:18px;\">" + "<a href =\"schoolpage.html?schoolid=" + school.id + "\">" + i + ". " + school.name + "</a>" + "</p>" +
        "</div>" +
        "</div>";

}

function linkCompare() {
    var b = document.getElementById("goCompare");
    var str = "";
    for (var i = 0; i < COMPARE.length; i++) {
        str += COMPARE[i] + ",";
    }
    b.href = "compare.html?" + str;
    console.log(b.href);
    return true;
}



function changeCenter(schoolResponse) {
    var centerlat = schoolResponse.x_center;
    var centerlon = schoolResponse.y_center;
    mymap.setView([centerlat, centerlon], 8);
}

function setHome(cont) {
    var redTag = new L.Icon({
        iconUrl: 'marker-icon-red.png'
    });

    var m = L.marker([content.originX, content.originY], {
        icon: redTag,
        iconSize: [32, 32],
        iconAnchor: [16, 32]
    }).addTo(markers)
    var ad = content.address;
    m.bindPopup(ad);
    mymap.addLayer(markers);
}

function execute() {
    document.getElementById("response").innerHTML = "";
    markers.clearLayers();
    content = requestSchoolList();
    changeCenter(content);
    fillResponseDiv(content.mostPopular);
    placeMarkersOnMap(content);
    if (content.originX) {
        setHome(content);
    }
    mymap.addLayer(markers);

}