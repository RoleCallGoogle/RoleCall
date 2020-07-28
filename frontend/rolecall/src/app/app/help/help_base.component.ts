import { Component } from '@angular/core';
import { HelpModalService } from './help_modal.service';


/**
 * The base component for the help test page.
 * Shows how to call the help modal.
 */
@Component({
  selector: 'app-root',
  templateUrl: './help_base.component.html',
  styleUrls: ['./help_base.component.scss']
})

export class HelpBaseComponent {

  constructor(private dialogService: HelpModalService) { }

  openDialog(): void { //Help Modal Text Components: Title, Body, 'Ok' button 
    const options = {
      title: 'Customer Help Modal',
      message: 'The Help Modal Works!',
      confirmText: 'OK!',
    };
    this.dialogService.open(options); //Displays Modal Text
  }
}
