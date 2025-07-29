import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { IOrganizationLocation } from '../organization-location.model';

@Component({
  selector: 'jhi-organization-location-detail',
  templateUrl: './organization-location-detail.component.html',
  imports: [SharedModule, RouterModule],
})
export class OrganizationLocationDetailComponent {
  organizationLocation = input<IOrganizationLocation | null>(null);

  previousState(): void {
    window.history.back();
  }
}
