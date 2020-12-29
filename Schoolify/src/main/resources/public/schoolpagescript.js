var DOMAIN = "http://localhost:8080";


function getQueryVariable(variable)
{
       var query = window.location.search.substring(1);
       var vars = query.split("&");
       for (var i=0;i<vars.length;i++) {
               var pair = vars[i].split("=");
               if(pair[0] == variable){return pair[1];}
       }
       return(false);
}

function getschoolinfo(url){
    var xmlHttp = new XMLHttpRequest();
    xmlHttp.open("GET", url, false);
    xmlHttp.send(null);
    var content = JSON.parse(xmlHttp.responseText);
    return content;
}

function createDetailedSchoolDescription(school) {
    return "<h4 class=\"mb-3\">"+ school.name +
        "</h4>" + "<p>"+
        "Typ: " + school.type + "<br>" +
        "Województwo: " + school.voivodeship + "<br>" +
        "Powiat: " + school.county + "<br>" +
        "Gmina: " + school.community + "<br>" +
        "Liczba oddziałów: " + school.branches + "<br>" +
        "Liczba uczniów: " + school.students + "<br>" +
        "Email: " + school.email + "<br>" +
        "Telefon: " + school.phoneNumber + "<br>" +
        "Strona internetowa: <a href=\"https://" + school.website + "\">" + school.website + "</a>" + "<br>" +
        "Adres: " + school.street + " " + school.houseNumber + ", " + school.town + "<br>"+
        "</p>"
    }


function getComments(id){
    var url = DOMAIN + "/comment/pid/" + id;
    var xmlHttp = new XMLHttpRequest();
    xmlHttp.open("GET", url, false);
    xmlHttp.send(null);
    var content = JSON.parse(xmlHttp.responseText);
    return content;
}

function sendComment(){
    var content = document.getElementById("comment").value 
    var user = document.getElementById("username").value 
    var rating = parseInt(document.getElementById("rating").value)
    var schoolid = parseInt(getQueryVariable("schoolid"))
    var xhr = new XMLHttpRequest();
    var params = "?schoolId="+schoolid+"&content=" + content + "&username=" + user + "&rate=" + rating;
    xhr.open("POST", DOMAIN+"/comment/add"+params, true);
    xhr.setRequestHeader("Content-Type", "application/json");
    xhr.send(null);
}

function displayComments(commentArray){
    for(var comment in commentArray){
        var newElement = document.createElement('div');
        newElement.id = commentArray[comment].id;
        newElement.className = "comment";
        newElement.innerHTML = 
       `
        <div class="row">
            <div class="col-md-10 username">
     ` + commentArray[comment].user + `</div>
            <div class="col-md-2">
                Ocena: ` + commentArray[comment].rate +
            `</div>
        </div>
        <div class="row"> <div class="col-md-12">`+ commentArray[comment].content + `</div> </div>`      
        commentArray[comment].schoolId + commentArray[comment].content;
        document.getElementById("comments").appendChild(newElement);
    }
}


function main(){

    var schoolid = getQueryVariable("schoolid");
    var url = DOMAIN + "/school/id/" + schoolid;
    var school = getschoolinfo(url);
    document.getElementById("info").innerHTML = createDetailedSchoolDescription(school);
    var comments = getComments(schoolid);
    displayComments(comments.comments);
    
}