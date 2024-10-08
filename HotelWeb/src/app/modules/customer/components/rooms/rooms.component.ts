import { Component } from '@angular/core';
import { NzMessageService } from 'ng-zorro-antd/message';
import { CustomerService } from '../../service/customer.service';
import { NzPaginationModule } from 'ng-zorro-antd/pagination';
import { NzTableModule } from 'ng-zorro-antd/table';
import { NzButtonModule } from 'ng-zorro-antd/button';  // Для кнопок действий
import { NzIconModule } from 'ng-zorro-antd/icon';
import {NzCardModule} from 'ng-zorro-antd/card';
import { NzAvatarModule } from 'ng-zorro-antd/avatar';
import { NzSkeletonModule } from 'ng-zorro-antd/skeleton'; 
import { RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { UserStorageService } from '../../../../auth/services/storage/user-storage.service';
import { NzModalModule } from 'ng-zorro-antd/modal';
import { NzDatePickerModule } from 'ng-zorro-antd/date-picker'; 
import { FormsModule } from '@angular/forms'; 

@Component({
  selector: 'app-rooms',
  standalone: true,
  imports: [
    FormsModule,
    NzDatePickerModule,
    NzModalModule,
    NzPaginationModule,
    NzSkeletonModule,
    NzAvatarModule,
    NzButtonModule, 
    NzTableModule,
    NzIconModule,
    NzCardModule,
    RouterModule,
    CommonModule,
  ],
  templateUrl: './rooms.component.html',
  styleUrl: './rooms.component.css'
})
export class RoomsComponent {
  currentPage = 1
  rooms = [];
  total: any;
  loading: boolean = false;

  constructor(private customerService: CustomerService,
    private message: NzMessageService,
  ){
    this.getRoom();
  }

  getRoom(){
    this.customerService.getAvailableRoom(this.currentPage - 1).subscribe(res => {
      this.rooms = res.roomDtoList;
      this.total = res.totalPages * 1;
    })
  }

  pageIndexChange(value: any){
    this.currentPage = value;
    this.getRoom();
  }

  isVisibleMiddle = false;
  date: Date[] = [];
  checkInDate: Date;
  checkOutDate: Date;
  id: number;

  onChange(result: Date[]){
    if(result.length == 2){
      this.checkInDate = result[0];
      this.checkOutDate = result[1];
    }
  }

  handleOkMiddle():void {
    const obj = {
      userId: UserStorageService.getUserId(),
      roomId: this.id,
      checkInDate: this.checkInDate,
      checkOutDate: this.checkOutDate
    }
    this.customerService.bookRoom(obj).subscribe(res => {
      this.message.success(
        'Request sibmitted for approval!', { nzDuration: 5000 }
      );
      this.isVisibleMiddle = false;
    }, error => {
      this.message.error(
        `${error.error}`, { nzDuration: 5000 }
      )
    })
  }

  showModalMiddle(id: number){
    this.id = id;
    this.isVisibleMiddle = true;
  }

  handleCancelMiddle(){
    this.isVisibleMiddle = false;
  }
}
