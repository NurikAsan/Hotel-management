import { Routes, RouterModule } from '@angular/router';
import { CustomerComponent } from './customer.component';
import { RoomsComponent } from './components/rooms/rooms.component';


export const croutes: Routes = [
    {path: '', component: CustomerComponent},
    {path: 'rooms', component: RoomsComponent}
]
