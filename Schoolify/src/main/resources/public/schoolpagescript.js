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
    var rate = parseInt(document.getElementsByClassName("active")[1].innerHTML);
    var schoolid = parseInt(getQueryVariable("schoolid"))
    var xhr = new XMLHttpRequest();
    var params = "?schoolId="+schoolid+"&content=" + content + "&username=" + user + "&rate=" + rate;
    xhr.open("POST", DOMAIN+"/comment/add"+params, true);
    xhr.setRequestHeader("Content-Type", "application/json");
    xhr.send(null);
}

function getNStars(n){
    var res = ""
    for(var i = 0; i < n; i++){
        res += "⭐";
    }
    return res;
}

function getVotingDiv(idComment){
    var voting = `<div class="column"> <div class="outer" id="vote${idComment}">
    <div class="inner"><button id="up${idComment}" style="border: 0; background: transparent" onclick="upVoteButton(${idComment})"><img src="upVote.png" width="20" height="20" alt="submit" /></button></div>
    <div class="inner"><button id="down${idComment}" style="border: 0; background: transparent" onclick="downVoteButton(${idComment})"><img src="downVote.png" width="20" height="20" alt="submit" /></button></div>
  </div></div>`;
  return voting;
}

function upVoteButton(idc){
    document.getElementById(`up${idc}`).disabled = true;
    var url = DOMAIN + "/comment/upvote/" + idc;
    var xhr = new XMLHttpRequest();
    xhr.open("POST", url, true);
    xhr.setRequestHeader("Content-Type", "application/json");
    xhr.send(null);

}

function downVoteButton(idc){
    document.getElementById(`down${idc}`).disabled = true;
    var url = DOMAIN + "/comment/downvote/" + idc;
    var xhr = new XMLHttpRequest();
    xhr.open("POST", url, true);
    xhr.setRequestHeader("Content-Type", "application/json");
    xhr.send(null);
}


function generateCommentText(comment){
    var today = new Date();
    var posted = new Date(comment.date);
    var mili = today.getTime() - posted.getTime();
    var daysDiff = Math.floor(mili/(1000*60*60*24));
    var res = getVotingDiv(comment.id) + `<div class="row" id=${comment.id}>` + 
            `<div class="col-md-10 username">` + comment.user + `</div>` + 
           `<div class="col-md-2 username">` + getNStars(comment.rate)  + 
            `</div>` + 
           `<div class="row"> <div class = "col-md-5">`+ `<p style="font-size:10px;"> Opublikowano : ` + daysDiff + ` dni temu.</p>` + 
        `</div>` + 
        `<div class="row"> <div class="col-md-12">`+ comment.content + `</div> </div>`  + '<br>' + '<br>';
    return res;

}

function displayComments(commentArray){
    var sum = 0;
    for(var comment in commentArray){
        var com = commentArray[comment];
        sum += com.rate;
        var newElement = document.createElement('div');
        newElement.id = commentArray[comment].id;
        newElement.className = "comment";
        newElement.innerHTML = generateCommentText(com);
        commentArray[comment].schoolId + commentArray[comment].content;
        document.getElementById("comments").appendChild(newElement);
    }
    var avg;
    if(commentArray.length > 0){
            avg = sum/commentArray.length;
    }else{
        avg = -1;
    }
    return avg
}

function putAvg(avg){

    var txt = "<br> Średnia ocena: <br>" +  getNStars(Math.round(avg));
    var nel = document.createElement('p');
    nel.innerHTML = txt;
    document.getElementById("avg").appendChild(nel);
}



function main(){

    var schoolid = getQueryVariable("schoolid");
    var url = DOMAIN + "/school/id/" + schoolid;
    var school = getschoolinfo(url);
    document.getElementById("info").innerHTML = createDetailedSchoolDescription(school);
    var comments = getComments(schoolid);
    var avg = displayComments(comments.comments);
    putAvg(avg);
    
}