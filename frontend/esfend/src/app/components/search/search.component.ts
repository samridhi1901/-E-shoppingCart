import { Component, EventEmitter, Input, Output } from '@angular/core';
import { Router } from '@angular/router';


@Component({
  selector: 'app-search',
  standalone:true,
  imports:[],
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.scss']
})
export class SearchComponent {
  @Output() searchEvent = new EventEmitter<string>();

  constructor(
    private router: Router
  ) {
  }

  doSearch(value: string): void {
    this.searchEvent.emit(value);
    console.log(value)
    // this.router.navigateByUrl(`/search/${value}`);
  }

}
