export default {
    URL: 'http://i3a201.p.ssafy.io:8080/cooking-0.0.2-SNAPSHOT/',
    ROUTES: {
        accounts: {
            signup: '/user/join2',
            login: '/user/login',
            logout: '/user/logout',
            baseuser: '/user',
            getcomments: '/user/comments/',
            checkemail: '/user/dup/email/',
            checknickname: '/user/dup/nickname/',
            checkpassword: '/user/password',
            getfollowers: '/user/followers/',
            checkkey: '/user/verification/check',
            requestkey: '/user/verification/send/',
            sendnewpassword: '/user/password/',
        },
        info: {

        },
        myrecipe: {
            myrecipes: '/recipe/all/',
            selectedrecipe: '/recipe/',
            fetchrecipesbytitle: 'recipe/title'
        },
        lookaroundrecipe:{
            getfilteredrecipes: '/recipe/recipes',
        },
    }
}