let stompClient;
let url= 'http://localhost:8080';
let selectedUser;
let userName;
let newMessages = new Map();


//V2
function connectToChat(userName) {
    console.log("connecting to chat...")
    let socket = new SockJS(url + '/stomp-endpoint');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);
        console.log("connected to: " + frame);
        stompClient.subscribe("/topic/messages/" + userName, function (response) {
            let data = JSON.parse(response.body);
            if (selectedUser === data.fromLogin) {
                render(data.message, data.fromLogin);
            } else {
                newMessages.set(data.fromLogin, data.message);
                $('#userNameAppender_' + data.fromLogin).append('<span id="newMessage_' + data.fromLogin + '" style="color: red">+1</span>');
            }
        });
    });
}

function showMessageOutput(messageOutput) {
    var response = document.getElementById('response');
    var p = document.createElement('p');
    p.appendChild(document.createTextNode(messageOutput.fromLogin + ": " 
      + messageOutput.message ));
    response.appendChild(p);
}
function render(message, userName) {
    // responses
    $("#name").append("<tr><td>"+ userName + "</td></tr>");
    $("#greetings").append("<tr><td>"+ message + "</td></tr>");
}

function addMessage() {
    var message = document.getElementById('theMessages').value;
    sendMessage(message);
}

function sendMessage(message) {
    username = userName;
    console.log(username)
    sendMsg(username, message);
    $("#greetings").append("<tr><td>"+ username + ": "+ message + "</td></tr>");
}


function sendMsg(username, message){ //to sendMessage
    var message = document.getElementById('theMessages').value;
    stompClient.send("/app/stomp-endpoint/"+selectedUser,{},JSON.stringify({ message:message, fromLogin:username }));

}

function registration(){
    userName = document.getElementById("name").value;
    $.get(url+"/registration/"+userName ,function (response) {
    }).fail(function (error){
        if(error.status === 400){
            alert("username already used!")
        }    
    })
}

function selectUser(userName) {
    console.log("selecting users: " + userName);
    selectedUser = userName;
    let isNew = document.getElementById("newMessage_" + userName) !== null;
    if (isNew) {
        let element = document.getElementById("newMessage_" + userName);
        element.parentNode.removeChild(element);
        render(newMessages.get(userName), userName);
    }
    $('#selectedUserId').html('');
    $('#selectedUserId').append('Chat with ' + userName);
}

function fetchAll(){
    $.get(url + "/fetchAllUsers", function (response) {
        let users = response;
        let usersTemplateHTML = "";
        for (let i = 0; i < users.length; i++) {
            usersTemplateHTML = usersTemplateHTML + '<a href="#" onclick="selectUser(\'' + users[i] + '\')"><li>\n' +
                '                    <div id="userNameAppender_' + users[i] + '" class="name">' + users[i] + '</div>\n' +
                '                    <div class="status">\n' +
                '                        <i class="fa fa-circle offline"></i>\n' +
                '                    </div>\n' +
                '            </li></a>';
        }
        $('#online').html(usersTemplateHTML);
    });
}

//============================================================

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    document.getElementById('response').innerHTML = '';
    if (connected) {
        $("#conversation").show();
    }
    else {
        $("#conversation").hide();
    }
    $("#greetings").html("");
}

/*function connect() { //login first
    var socket = new SockJS('/stomp-endpoint');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/messages', function (greeting) { //from message controller
            showGreeting(JSON.parse(greeting.body).messages);
        });
    });
}
*/

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $( "#connect" ).click(function() { connectToChat(); });
    $( "#disconnect" ).click(function() { disconnect(); });
    $( "#send" ).click(function() { sendMessage(); });
});