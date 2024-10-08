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

@Component({
  selector: 'app-rooms',
  standalone: true,
  imports: [
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
}
