import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ProvisionercloudTestModule } from '../../../test.module';
import { NetKeyDetailComponent } from 'app/entities/net-key/net-key-detail.component';
import { NetKey } from 'app/shared/model/net-key.model';

describe('Component Tests', () => {
  describe('NetKey Management Detail Component', () => {
    let comp: NetKeyDetailComponent;
    let fixture: ComponentFixture<NetKeyDetailComponent>;
    const route = ({ data: of({ netKey: new NetKey(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ProvisionercloudTestModule],
        declarations: [NetKeyDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(NetKeyDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(NetKeyDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.netKey).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
