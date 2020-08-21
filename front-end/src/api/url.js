export default {
<<<<<<< HEAD
    URL: 'http://d269e2b2d442.ngrok.io',
=======
    URL: 'https://i3a201.p.ssafy.io:8443/cooking-0.0.1-SNAPSHOT/',
>>>>>>> develop
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
<<<<<<< HEAD
            checkpassword: '/user/check/password',
=======
            checkpassword: '/user/token/password',
>>>>>>> develop
            getfollowers: '/user/followers/',
            checkkey: '/user/verification/check/',
            requestkey: '/user/verification/send/',
            sendnewpassword: '/user/password/',
            follow: '/user/token/follow/',
            unfollow: '/user/token/unfollow/',
            follower: '/user/followers/',
            following: '/user/following/',
            hituser: '/user/hits/'
        },
        info: {
            getuserinfo: '/user/',
        },
        myrecipe: {
            myrecipes: '/recipe/all/',
            selectedrecipe: '/recipe/',
            fetchrecipesbytitle: '/recipe/title',
            recipesave: '/recipe/save',
            recipelike: '/recipe/token/',
            commentcreate: '/recipe/token/',
            fetchcomments: '/recipe/',
            deletecomment: '/recipe/token/',
            updatecomment: '/recipe/token/'
        },
<<<<<<< HEAD
=======
        lookaroundrecipe:{
            getfilteredrecipes: '/recipe/recipes2',
            getingredients: '/recipe/ingredients',
        },
        recipeview: {
            fetchrecipe: '/recipe/'
        },
        editor: {
            saveRecipe: '/recipe/token/save',
            updateRecipe: '/recipe/token/revise',
            deleteRecipe: '/recipe/token/'
        },
        home: {
            randomRecipe: '/recipe/random'
        }
>>>>>>> develop
    }
}