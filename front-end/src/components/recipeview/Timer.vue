<template>
    <div>
        <div class="timer">
        <div class="timer--clock">
            <div class="minutes-group clock-display-grp">
                <div class="first number-grp">
                    <div class="number-grp-wrp">
                    <div class="num num-0"><p>0</p></div>
                    <div class="num num-1"><p>1</p></div>
                    <div class="num num-2"><p>2</p></div>
                    <div class="num num-3"><p>3</p></div>
                    <div class="num num-4"><p>4</p></div>
                    <div class="num num-5"><p>5</p></div>
                    <div class="num num-6"><p>6</p></div>
                    <div class="num num-7"><p>7</p></div>
                    <div class="num num-8"><p>8</p></div>
                    <div class="num num-9"><p>9</p></div>
                    </div>
                </div>
                <div class="second number-grp">
                    <div class="number-grp-wrp">
                    <div class="num num-0"><p>0</p></div>
                    <div class="num num-1"><p>1</p></div>
                    <div class="num num-2"><p>2</p></div>
                    <div class="num num-3"><p>3</p></div>
                    <div class="num num-4"><p>4</p></div>
                    <div class="num num-5"><p>5</p></div>
                    <div class="num num-6"><p>6</p></div>
                    <div class="num num-7"><p>7</p></div>
                    <div class="num num-8"><p>8</p></div>
                    <div class="num num-9"><p>9</p></div>
                    </div>
                </div>
            </div>
            <div class="clock-separator"><p>:</p></div>
            <div class="seconds-group clock-display-grp">
                <div class="first number-grp">
                    <div class="number-grp-wrp">
                    <div class="num num-0"><p>0</p></div>
                    <div class="num num-1"><p>1</p></div>
                    <div class="num num-2"><p>2</p></div>
                    <div class="num num-3"><p>3</p></div>
                    <div class="num num-4"><p>4</p></div>
                    <div class="num num-5"><p>5</p></div>
                    <div class="num num-6"><p>6</p></div>
                    <div class="num num-7"><p>7</p></div>
                    <div class="num num-8"><p>8</p></div>
                    <div class="num num-9"><p>9</p></div>
                    </div>
                </div>
                <div class="second number-grp">
                    <div class="number-grp-wrp">
                    <div class="num num-0"><p>0</p></div>
                    <div class="num num-1"><p>1</p></div>
                    <div class="num num-2"><p>2</p></div>
                    <div class="num num-3"><p>3</p></div>
                    <div class="num num-4"><p>4</p></div>
                    <div class="num num-5"><p>5</p></div>
                    <div class="num num-6"><p>6</p></div>
                    <div class="num num-7"><p>7</p></div>
                    <div class="num num-8"><p>8</p></div>
                    <div class="num num-9"><p>9</p></div>
                    </div>
                </div>
            </div>
        </div>
        </div>
        <div class="reload">
        <h2>THE END</h2>
        </div>
    </div>
</template>

<script>
export default {
    name:'Timer',
    data(){
        return {

        }
    },
    methods:{
        
    }
}
</script>

<style scoped>
    /*================= * TIMER *=================*/
    .timer {
        width: 550px;
        height: 248px;
        display: block;
        position: absolute;
        top: 50%;
        left: 50%;
        margin-left: -275px;
        margin-top: -124px;
        color: white;
    }
    .timer * {
        cursor: default;
    }
    .timer h3 {
        width: 100%;
        font-size: 26px;
        letter-spacing: 4px;
        text-align: center;
    }
    .timer--clock {
        width: 100%;
        position: relative;
        padding-left: 6px;
        margin-top: 22px;
        overflow: hidden;
    }
    .timer--clock .clock-display-grp {
        width: 100%;
        position: relative;
    }
    .timer--clock .clock-display-grp .number-grp {
        width: auto;
        display: block;
        height: 156px;
        float: left;
        overflow: hidden;
    }
    .timer--clock .clock-display-grp .number-grp .number-grp-wrp {
        width: 100%;
        position: relative;
    }
    .timer--clock .clock-display-grp .number-grp .number-grp-wrp .num {
        width: 100%;
        position: relative;
        height: 156px;
    }
    .timer--clock .clock-display-grp .number-grp .number-grp-wrp .num p {
        width: auto;
        display: table;
        font-size: 205px;
        line-height: 150px;
        font-weight: bold;
    }
    .timer--clock .clock-separator {
        width: auto;
        float: left;
        display: block;
        height: 156px;
    }
    .timer--clock .clock-separator p {
        width: auto;
        display: table;
        font-size: 205px;
        line-height: 150px;
        font-weight: bold;
    }
    .timer h4 {
        width: 100%;
        font-size: 10px;
        letter-spacing: 6px;
        text-align: center;
        padding-top: 25px;
        float: left;
    }
    .reload {
        width: 125px;
        height: 14px;
        position: absolute;
        bottom: 50vh;
        left: 50%;
        margin-left: -62.5px;
        opacity: 0;
        display: none;
        z-index: 9999;
        color: black;
    }
    .reload:hover svg path {
        fill: #666;
    }
    .reload:hover p {
        color: #666;
    }
    .reload svg {
        width: 12px;
        height: 12px;
        float: left;
        margin-right: 10px;
    }
    .reload p {
        color: #2b2b2b;
        font-size: 12px;
        float: left;
        line-height: 11px;
    }
</style>