export default {
<<<<<<< HEAD
    URL: 'http://d269e2b2d442.ngrok.io',
=======
    URL: 'http://e7c0ebd8068d.ngrok.io',
>>>>>>> master
    ROUTES: {
        accounts: {
            signup: '/user/join',
            login: '/user/login',
            logout: '/user/logout',
            baseuser: '/user',
            getcomments: '/user/comments/',
            checkemail: '/user/dup/email/',
            checknickname: '/user/dup/nickname/',
            checkpassword: '/user/check/password',
            getfollowers: '/user/followers/',
            checkkey: '/user/verification/check/',
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
    }
}