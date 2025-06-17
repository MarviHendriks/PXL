<template>
<div>
    <div>
        <div v-if="$store.state.user">
            <div v-if="$store.state.user.roles.includes('admin')">
                <h1>Admin</h1>
                <pre>{{$store.state.user}}</pre>
            </div>
            <div v-else-if="$store.state.user.roles.includes('user')">
                <h1>User: {{$store.state.user.username}}</h1>
                <button @click="retrieveOwnTasks">Retrieve my tasks</button>
                <div v-if="$store.state.tasks.length">
                    <hr>
                    <div  v-for="task,index in $store.state.tasks">
                        <UpdatableTask v-bind:task="task" v-bind:index="index"></UpdatableTask> 
                    </div>
                </div>
            </div>
        <div>
            <hr>
            <button @click="logout">Log out</button>
        </div>
        </div>
        <div v-else>
                username:<br> 
                <input type="text" v-model="user.username"><br>
                password:<br>
                <input type="password" v-model="user.password"><br><br>
                <button @click="login">Login</button>
            </div>
     </div>
    <div v-if="$store.state.error">
            {{$store.state.error}}
    </div>
</div>
</template>
<script>
import UpdatableTask from "./UpdatableTask.vue";
export default {
    name: "User",
    components: {
        UpdatableTask
    },
    data() {
      return {
            user: {username:"", password:""}
        }
    },
    methods: {
        login: function(){    
            this.$store.dispatch("login", {username:this.user.username, password: this.user.password});
        },
        logout: function(){
            this.$store.dispatch("logout");
        },

        retrieveOwnTasks: function(){    
            this.$store.dispatch("retrieveOwnTasks");
        }
    },
    beforeMount(){
        this.$store.dispatch("checkLogin");
    }
}
</script>
