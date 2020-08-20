export default {
    URL: 'https://i3a201.p.ssafy.io:8443/cooking-0.0.1-SNAPSHOT/',
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
    }
}