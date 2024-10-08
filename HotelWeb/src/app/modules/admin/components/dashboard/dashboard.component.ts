import { Component } from '@angular/core';
import { NzMessageService } from 'ng-zorro-antd/message';
import { AdminService } from '../../admin-services/admin.service';
import { NzPaginationModule } from 'ng-zorro-antd/pagination';
import { NzTableModule } from 'ng-zorro-antd/table';
import { NzButtonModule } from 'ng-zorro-antd/button';  // Для кнопок действий
import { NzIconModule } from 'ng-zorro-antd/icon';
import {NzCardModule} from 'ng-zorro-antd/card';
import { NzAvatarModule } from 'ng-zorro-antd/avatar';
import { NzSkeletonModule } from 'ng-zorro-antd/skeleton'; 
import { RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import {NzModalModule, NzModalService } from 'ng-zorro-antd/modal';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [
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
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.css'
})
export class DashboardComponent {
  currentPage = 1;
  rooms = [];
  total: any;
  loading: boolean = false;

  constructor(private adminService: AdminService,
      private message: NzMessageService,
      private modalService: NzModalService
  ){
    this.getRoom();
  }

  getRoom(){
    this.adminService.getRooms(this.currentPage - 1).subscribe(res => {
      this.rooms = res.roomDtoList;
      this.total = res.totalPages * 1;
    })
  }

  pageIndexChange(value: any){
    this.currentPage = value;
    this.getRoom();
  }

  showConfirm(id: number){
    this.modalService.confirm({
      nzTitle: 'Confirm',
      nzContent: 'Do you want to delete this room?',
      nzOkText: 'Delete',
      nzCancelText: 'Cancel',
      nzOnOk: () => this.deleteRoom(id)
    }
    )
  }

  deleteRoom(id: number) {
    this.adminService.deleteRoom(id).subscribe(res => {
      this.message.success('Room deleted', {nzDuration: 5000});
      this.getRoom();
    }, err => {
      this.message.error(`${err.error}`, {nzDuration: 5000})
    })
  }
}
