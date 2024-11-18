import { Component } from '@angular/core';
import { CustomerService } from '../../service/customer.service';
import {NzCardModule} from 'ng-zorro-antd/card';
import { NzMessageService } from 'ng-zorro-antd/message';
import { NzTableModule } from 'ng-zorro-antd/table';
import { ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { NzPaginationModule } from 'ng-zorro-antd/pagination';
import { NzTagModule } from 'ng-zorro-antd/tag'; 
import { NzIconModule } from 'ng-zorro-antd/icon';


@Component({
  selector: 'app-view-bookings',
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
  templateUrl: './view-bookings.component.html',
  styleUrl: './view-bookings.component.css'
})
export class ViewBookingsComponent {
  currentPage: any = 1;

  total: any;
  bookings: any;

  constructor(private customService: CustomerService,
    private message: NzMessageService){
      this.getBookings();
    }

  getBookings(){
    this.customService.getBookingsById(this.currentPage - 1).subscribe(res =>{
      console.log(res)
      // this.bookings = res.reservationDtoList;
      // this.total = res.totalPages * 5;
    }, err => {
      this.message.error(
        `${err.error}`,
        { nzDuration: 5000 }
      )
    })
  }

  pageIndexChange(value: any){
      this.currentPage = value;
      this.getBookings();
  }
}
