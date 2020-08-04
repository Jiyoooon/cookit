export default {
    URL: 'http://i3a201.p.ssafy.io:8080/cooking-0.0.1-SNAPSHOT/',
    ROUTES: {
        accounts: {
            signup: '/user/join',
            login: '/user/login',
            logout: '/user/token/logout',
            baseuser: '/user/token',
            fetchuserinfo: '/user/',
            getcomments: '/user/comments/',
            checkemail: '/user/dup/email/',
            checknickname: '/user/dup/nickname/',
            checkpassword: '/user/token/password',
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
            getingredients: '/recipe/ingredients',
        },
        recipeview: {
            fetchrecipe: '/recipe/'
        },
    }
}