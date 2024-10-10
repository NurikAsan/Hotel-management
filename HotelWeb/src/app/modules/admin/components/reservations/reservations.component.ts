import { Component } from '@angular/core';
import {NzCardModule} from 'ng-zorro-antd/card';
import { NzMessageService } from 'ng-zorro-antd/message';
import { AdminService } from '../../admin-services/admin.service';
import { NzTableModule } from 'ng-zorro-antd/table';
import { ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { NzPaginationModule } from 'ng-zorro-antd/pagination';
import { NzTagModule } from 'ng-zorro-antd/tag'; 
import { NzIconModule } from 'ng-zorro-antd/icon';


@Component({
  selector: 'app-reservations',
  standalone: true,
  imports: [
    NzCardModule,
    NzTableModule,
    ReactiveFormsModule,
    CommonModule,
    NzPaginationModule,
    NzTagModule,
    NzIconModule
  ],
  templateUrl: './reservations.component.html',
  styleUrl: './reservations.component.css'
})
export class ReservationsComponent {
    currentPage: any = 1;
    total: any;
    reservations: any;

    constructor(
      private adminService: AdminService,
      private message: NzMessageService,
      ){
        this.getReservations();
      }

    getReservations(){
      this.adminService.getReservations(this.currentPage - 1).subscribe(res => {
        this.reservations = res.reservationDtoList;
        this.total = res.totalPages*5;
      }, err => {
          this.message.error(`${err.error}`, { nzDuration: 5000})
      })
    }

    pageIndexChange(value: any){
      this.currentPage = value;
      this.getReservations();
    }

    changeReservationStatus(id: number, status:string){
      this.adminService.changeReservationStatus(id, status).subscribe(res => {
        this.message.success(
          'Reservation status updated successfuly',
          {nzDuration: 5000}
        );
        this.getReservations();
      }, err => {
        this.message.error(
          `${err.error}`,
          {nzDuration: 5000}
        );
      })
    }
}
