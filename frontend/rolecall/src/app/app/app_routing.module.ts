import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomepageModule } from '../homepage/homepage.module';
import { HomepageBase } from '../homepage/homepage_base.component';
import { SettingsModule } from '../settings/settings.module';
import { SettingsBase } from '../settings/settings_base.component';
import { HelpModule } from './help/help.module';
import { HelpBaseComponent } from './help/help_base.component';

const routes: Routes = [
  { path: '', component: HomepageBase },
  { path: 'help', component: HelpBaseComponent},
  { path: 'settings', component: SettingsBase },
  { path: '**', redirectTo: '/', pathMatch: 'full' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes),
    HomepageModule,
    SettingsModule,
    HelpModule,
  ],
  exports: [RouterModule]
})
export class AppRoutingModule { }
