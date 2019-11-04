import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ProvisionercloudTestModule } from '../../../test.module';
import { NetworkConfigurationDetailComponent } from 'app/entities/network-configuration/network-configuration-detail.component';
import { NetworkConfiguration } from 'app/shared/model/network-configuration.model';

describe('Component Tests', () => {
  describe('NetworkConfiguration Management Detail Component', () => {
    let comp: NetworkConfigurationDetailComponent;
    let fixture: ComponentFixture<NetworkConfigurationDetailComponent>;
    const route = ({ data: of({ networkConfiguration: new NetworkConfiguration(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ProvisionercloudTestModule],
        declarations: [NetworkConfigurationDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(NetworkConfigurationDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(NetworkConfigurationDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.networkConfiguration).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
