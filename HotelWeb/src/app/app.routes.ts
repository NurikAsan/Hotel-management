import { Routes } from '@angular/router';
import { LoginComponent } from './auth/components/login/login.component';
import { RegisterComponent } from './auth/components/register/register.component';
import { CustomerComponent } from './modules/customer/customer.component';
import { croutes } from './modules/customer/customer.routes'
import { aroutes } from './modules/admin/admin.routes'
import { AdminComponent } from './modules/admin/admin.component';

export const routes: Routes = [
    {path:'register', component: RegisterComponent},
    {path:'', component: LoginComponent},
    {
        path:'customer', children: croutes
    },
    {
        path: 'admin', children: aroutes
    }
];
