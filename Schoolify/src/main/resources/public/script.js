var mymap = L.map('mapid', {
    preferCanvas: true
}).setView([52, 18], 6);
L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
    attribution: '&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
}).addTo(mymap);
var markers = L.layerGroup();

function createRequestURI() {

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

    var base = "http://localhost:8080/search?";

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
        if (type == "Dowolna") { }
        else {
            var key = encodeURIComponent("type");
            var value = encodeURIComponent(type);
            base += key + "=" + value + "&";
        }
    }
    if (studlower || studupper) {
        var key1 = encodeURIComponent("studlower");
        var value1 = encodeURIComponent(studlower);
        var key2 = encodeURIComponent("studupper");
        var value2 = encodeURIComponent(studupper);
        base += key1 + "=" + value1 + "&" + key2 + "=" + value2 + "&";
    }
    if (branlower || branupper) {
        var key1 = encodeURIComponent("branlower");
        var value1 = encodeURIComponent(branlower);
        var key2 = encodeURIComponent("branupper");
        var value2 = encodeURIComponent(branupper);
        base += key1 + "=" + value1 + "&" + key2 + "=" + value2 + "&";
    }
    if (voivodeship) {
        if (voivodeship == "Dowolne") { }
        else {
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
        "Liczba uczniów: " + school.voivodeship + "<br>" +
        "Email: " + school.email + "<br>" +
        "Telefon: " + school.phoneNumber + "<br>" +
        "Strona internetowa: <a href=\"" + school.website + "\">" + school.website + "</a>" + "<br>" +
        "Adres: " + school.street + " " + school.houseNumber + ", " + school.town + "<br><br>";
}


function fillResponseDiv(schoolArray) {
    for (var school in schoolArray) {
        var newElement = document.createElement('div');
        newElement.id = schoolArray[school].id;
        newElement.className = "school";
        newElement.innerHTML = createSchoolDescription(schoolArray[school]);
        document.getElementById("response").appendChild(newElement);
    }

}

function placeMarkersOnMap(schoolArray) {
    schoolArray.forEach(element => {
        var marker = L.circleMarker([element.lat, element.lon], {
            radius: 7
        }).addTo(markers);
        var text = createSchoolDescription(element);
        marker.bindPopup(text);
    });
    mymap.addLayer(markers);
}

// fcja zoomowania
function zoomFunction(schoolArray){

}



function setBoundries(schoolArray){
	var midlon = 0;
	var midlat = 0;
	var no_outliers_lon = [];
	var no_outliers_lat = [];
	var n = schoolArray.length;
	var mean_lon = 0;
	var mean_lat = 0;
    for(var c in schoolArray){
    	mean_lon += c.lon;
    	mean_lat += c.lat;
    	no_outliers_lat.push(c.lat);
    	no_outliers_lon.push(c.lon);
    }
    mean_lat /= n;
    mean_lon /= n;
    var latstd,lonstd;
    try{
	    latstd = Math.std(no_outliers_lat);
    	lonstd = Math.std(no_outliers_lon);
	    no_outliers_lon = no_outliers_lon.filter(a => mean_lon -3*lonstd < a < mean_lon + 3*lonstd );
		no_outliers_lat = no_outliers_lat.filter(a => mean_lat -3*latstd < a < mean_lat + 3*latstd );
		var center_zoomlon = 0;
		for(var x in no_outliers_lon){
			center_zoomlon += x;
		}
		center_zoomlon /= no_outliers_lon.length;
		var center_zoomlat = 0;
		for(var x in no_outliers_lat){
			center_zoomlat += x;
		}
		center_zoomlat /= no_outliers_lat.length;

		mymap.setView([center_zoomlat.length,center_zoomlon],6)
    }catch(e){
    	console.log(e.message);
    }
    
}



function execute() {

    document.getElementById("response").innerHTML = "";

    markers.clearLayers();
    content = requestSchoolList();
    setBoundries(content)
    console.log(content);

    fillResponseDiv(content);
    placeMarkersOnMap(content);

}