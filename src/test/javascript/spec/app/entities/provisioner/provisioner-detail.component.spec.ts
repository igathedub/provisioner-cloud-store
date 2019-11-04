import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ProvisionercloudTestModule } from '../../../test.module';
import { ProvisionerDetailComponent } from 'app/entities/provisioner/provisioner-detail.component';
import { Provisioner } from 'app/shared/model/provisioner.model';

describe('Component Tests', () => {
  describe('Provisioner Management Detail Component', () => {
    let comp: ProvisionerDetailComponent;
    let fixture: ComponentFixture<ProvisionerDetailComponent>;
    const route = ({ data: of({ provisioner: new Provisioner(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ProvisionercloudTestModule],
        declarations: [ProvisionerDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ProvisionerDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ProvisionerDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.provisioner).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
