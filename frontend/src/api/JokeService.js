import {API_URL} from './Constants'


export function onCreate({title, content, keyword, username}) {
    let joke = {title, content, keyword, username};
    return fetch(`${API_URL}/joke`, {
        method: 'POST',
        body: JSON.stringify(joke),
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${sessionStorage.getItem('token')}`
        }
    });
}

export function onAddComment({content, jokeId, ownerOfComment}) {
    let comment = {content, jokeId, ownerOfComment};
    return fetch(`${API_URL}/add/comment/:${jokeId}`, {
        method: 'POST',
        body: JSON.stringify(comment),
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${sessionStorage.getItem('token')}`
        }
    });
}


export function onDeleteComment(id) {
    return fetch(`${API_URL}/add/comment/:${id}`, {
        method: 'DELETE',
        headers: {
            'Authorization': `Bearer ${sessionStorage.getItem('token')}`
        }
    });
}

export function addLike(username, id) {
    return fetch(`${API_URL}/add/like/:${id}/:${username}`, {
        method: 'POST',
        headers: {
            'Authorization': `Bearer ${sessionStorage.getItem('token')}`
        }
    });
}

export function getJokeByUsername() {
    let username = sessionStorage.getItem('authenticatedUser');
    return fetch(`${API_URL}/joke-manage/:${username}`,
        {
            method: 'GET',
            headers: {
                'Authorization': `Bearer ${sessionStorage.getItem('token')}`
            }
        });
}

export function getJokeById(id) {
    return fetch(`${API_URL}/joke/:${id}`,
        {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${sessionStorage.getItem('token')}`
            }
        });
}

export function editJokeById({title, keyword, content, id}) {
    let joke = {title, keyword, content, id};
    return fetch(`${API_URL}/joke/:${id}`,
        {
            method: 'PUT',
            body: JSON.stringify(joke),
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${sessionStorage.getItem('token')}`
            }
        });
}

export function deleteJokeById({id}) {
    return fetch(`${API_URL}/joke/:${id}`,
        {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${sessionStorage.getItem('token')}`
            }
        });
}

export  function getLastTheeJokes() {
    return  fetch(`${API_URL}/lastTheeJokes`, {
        method: 'GET',
    });
}

export function getJokesFromDbByKeyword(keyword) {
    return fetch(`${API_URL}/jokes-by-keyword/:${keyword}`,
        {
            method: 'GET',
            headers: {
                'Authorization': `Bearer ${sessionStorage.getItem('token')}`
            }
        });
}



export function getJokeByMostLikes() {
    return fetch(`${API_URL}/jokes-by-most-likes`,
        {
            method: 'GET',
            headers: {
                'Authorization': `Bearer ${sessionStorage.getItem('token')}`
            }
        });
}

