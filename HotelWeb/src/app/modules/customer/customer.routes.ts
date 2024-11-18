import { Routes, RouterModule } from '@angular/router';
import { CustomerComponent } from './customer.component';
import { RoomsComponent } from './components/rooms/rooms.component';
import { ViewBookingsComponent } from './components/view-bookings/view-bookings.component';


export const croutes: Routes = [
    {path: '', component: CustomerComponent},
    {path: 'rooms', component: RoomsComponent},
    {path: 'bookings', component: ViewBookingsComponent},
]
