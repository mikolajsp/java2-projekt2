var DOMAIN = "http://localhost:8080";
var distsGlobal = new Array;

function getSchoolsToCompare(){
	var param = window.location.search.substring(1);
	var ids = param.split("&");
	var url = DOMAIN + "/school/ids/" + ids.join();
	console.log(url);
    var xmlHttp = new XMLHttpRequest();
    xmlHttp.open("GET", url, false);
    xmlHttp.send(null);
    var content = JSON.parse(xmlHttp.responseText);
    return content;
}


function customize(lat,lon,name,map){
	map.setView([lat,lon],11);
    var redTag = new L.Icon({
          iconUrl: 'marker-icon-red.png'
    }
    );
    var m = L.marker([lat, lon], {
        icon: redTag,
         iconSize: [32, 32],
        iconAnchor: [16,32]
    }).addTo(map)
    m.bindPopup(name);
}


function makeNameRow(schools,n){
	var thisRow = document.getElementById("nameRow");
	for(var i = 0; i < n; i++){
		var nextCol = document.createElement("td");
		nextCol.innerHTML = schools[i].school.name;
		thisRow.appendChild(nextCol);
	}
}

function makeMapRow(schools,n){
	var thisRow = document.getElementById("positionRow");
	for(var i = 0; i < n; i++){
		var nextCol = document.createElement("td");
		thisRow.appendChild(nextCol);
		nextCol.id = `mapid${i}`;
		var mymap = L.map(`mapid${i}`, {
			    preferCanvas: true
			});
		L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
			    attribution: '&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
			}).addTo(mymap);
		var s = schools[i].school;
		var lat = s.lat;
		var lon = s.lon;
		var name = s.name;
		customize(lat,lon,name,mymap);
	}
}



function makeAvgRow(schools,n){
	var thisRow = document.getElementById("avgRow");
	for(var i = 0; i < n; i++){
		var nextCol = document.createElement("td");
		nextCol.innerHTML = getNBlue(schools[i].avg);
		console.log(schools[i].avg);
		thisRow.appendChild(nextCol);
	}
}

function makeRadarRow(schools,n){
	var thisRow = document.getElementById("radarRow");
	for(var i = 0; i < n; i++){
		var nextCol = document.createElement("td");
		thisRow.appendChild(nextCol);
		var canvas = document.createElement("canvas");
		canvas.height = 300;
		canvas.width = 300;
		nextCol.appendChild(canvas);
		canvas.id = `radar${i}`;
		var s = schools[i];
		createRadar(s.school.name,s.assesment,canvas.id);
		thisRow.appendChild(nextCol);
	}
}



function calcCrow(lat1, lon1, lat2, lon2){
  var R = 6371; 
  var dLat = toRad(lat2-lat1);
  var dLon = toRad(lon2-lon1);
  var lat1 = toRad(lat1);
  var lat2 = toRad(lat2);

  var a = Math.sin(dLat/2) * Math.sin(dLat/2) +
    Math.sin(dLon/2) * Math.sin(dLon/2) * Math.cos(lat1) * Math.cos(lat2); 
  var c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a)); 
  var d = R * c;
  return d;
}

function toRad(Value) 
{
    return Value * Math.PI / 180;
}




function positionCapture(Slat,Slon,col){
	navigator.geolocation.getCurrentPosition(function(position) {
    let lat = position.coords.latitude;
    let long = position.coords.longitude;
    var d = calcCrow(lat,long,Slat,Slon).toFixed(1);
    col.innerHTML = "<b>" + d + "</b>";
  }, function(){
  	col.innerHTML = "Błąd lokalizacji."
  });
}


function makeDistanceRow(schools,n){
	var thisRow = document.getElementById("distanceRow");
	for(var i = 0; i < n; i++){
		var nextCol = document.createElement("td");
		var s = schools[i].school;
		positionCapture(s.lat,s.lon,nextCol);
		thisRow.appendChild(nextCol);
	}

}

function makeOpinionRow(schools,n){
	var thisRow = document.getElementById("bestCommentRow");
	for(var i = 0; i < n; i++){
		var nextCol = document.createElement("td");
		var s = schools[i];
		nextCol.innerHTML =  s.bestComment.content + "<br>" + "<pre style=\"text-align: right;\">"+"<img src=upVote.png width=\"20\" height=\"20\" >  "+ s.bestComment.upVotes +"</img>"
		+ "  <img src=downVote.png width=\"20\" height=\"20\">  " + s.bestComment.downVotes +"  </img>" + "</pre>";
		thisRow.appendChild(nextCol);
	}
}



function makeCols(schools,n){
	var table = document.getElementById("comparision"); 
	makeNameRow(schools,n);
	makeMapRow(schools,n);
	makeAvgRow(schools,n);
	makeRadarRow(schools,n);
	makeDistanceRow(schools,n);	
	makeOpinionRow(schools,n);	
}


function main(){
	var response_cont = getSchoolsToCompare();
	var columns_no = response_cont.length;
	makeCols(response_cont,columns_no);
}